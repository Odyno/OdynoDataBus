package net.staniscia.odynodatabus.net;

import com.hazelcast.config.ClasspathXmlConfig;
import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.ITopic;
import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.staniscia.odynodatabus.DataBusService;
import net.staniscia.odynodatabus.Publisher;
import net.staniscia.odynodatabus.Subscriber;
import net.staniscia.odynodatabus.filters.Filter;
import net.staniscia.odynodatabus.msg.Envelop;
import net.staniscia.odynodatabus.net.core.*;

/**
 * implementazione distribuita del
 */
public class DataBusCluster implements DataBusService {

    /**
     * The Constant LOGGER.
     */
    private static final Logger LOGGER = Logger.getLogger(DataBusCluster.class.getName());
    private HazelcastInstance hzCloud;
    private ITopic<Envelop<?>> hzDistriuitedTopic;
    /**
     * The write.
     */
    private Lock lockConcurrentList;
    /**
     * The subscribers.
     */
    private final Map<String, TopicConsumer> subscribers;

    public DataBusCluster() {
        Config cfg = null;
        try {
            cfg = new ClasspathXmlConfig("OdynoDatabus-hazelcast.xml");
        } catch (Exception e) {
            cfg = new Config();
        }
        hzCloud = Hazelcast.newHazelcastInstance(cfg);
        hzDistriuitedTopic = hzCloud.getTopic("databus-default-topic");
        subscribers = hzCloud.getMap("subscribers-default-topic");
        lockConcurrentList = hzCloud.getLock(subscribers);
    }

    @Override
    public <T extends Serializable> Publisher<T> getDataPublisher(Class<T> objectType) {
        LOGGER.log(Level.FINEST, "Returned one publisher on OdynoDataBus");
        return new TopicProducer<T>(hzDistriuitedTopic);
    }

    @Override
    public <T extends Serializable> boolean registerSubscriber(Subscriber<T, ? extends Filter<T>> subscriver) {
        LOGGER.log(Level.FINEST, "request Subscription  on OdynoDataBus");

        if (!subscribers.containsKey(subscriver.getIdentification())) {
            TopicConsumer topicConsumer = new TopicConsumer(subscriver);
            lockConcurrentList.lock();
            try {
                hzDistriuitedTopic.addMessageListener(topicConsumer);

            } finally {
                lockConcurrentList.unlock();
            }
            subscribers.put(subscriver.getIdentification(), topicConsumer);
            return true;
        }
        return false;
    }

    @Override
    public <T extends Serializable> boolean unRegisterSubscriber(String subscriverId) {
        if (subscribers.containsKey(subscriverId)) {
            lockConcurrentList.lock();
            try {
                TopicConsumer obj = subscribers.get(subscriverId);
                hzDistriuitedTopic.removeMessageListener(obj);
                subscribers.remove(subscriverId);
            } finally {
                lockConcurrentList.unlock();
            }
            return true;
        }
        return false;
    }
}
