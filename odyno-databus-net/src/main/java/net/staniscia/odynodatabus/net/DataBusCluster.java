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
import net.staniscia.odynodatabus.DataBusServiceStatus;
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
            LOGGER.log(Level.WARNING, "No File Found, load standard Config. Cause: ", e);
            cfg = new Config();
        }
        hzCloud = Hazelcast.newHazelcastInstance(cfg);
        hzDistriuitedTopic = hzCloud.getTopic("databus-default-topic");
        subscribers = hzCloud.getMap("subscribers-default-topic");
        lockConcurrentList = hzCloud.getLock(subscribers);
    }

    @Override
    public <T extends Serializable> Publisher<T> getDataPublisher(final Class<T> objectType) {
        LOGGER.log(Level.INFO, "Returned one publisher on OdynoDataBus");
        return new TopicProducer(hzDistriuitedTopic);
    }

    @Override
    public <T extends Serializable> boolean registerSubscriber(Subscriber<T, ? extends Filter<T>> subscriver) {
        boolean reason =false;
        LOGGER.log(Level.INFO, "request Subscription  on OdynoDataBus");
        if (!subscribers.containsKey(subscriver.getIdentification())) {
            TopicConsumer topicConsumer = null;
            lockConcurrentList.lock();
            try {
                topicConsumer = new TopicConsumer(subscriver);
                hzDistriuitedTopic.addMessageListener(topicConsumer);
                subscribers.put(subscriver.getIdentification(), topicConsumer);
                topicConsumer.onChangeSystemStatus(DataBusServiceStatus.RUNNING);
                reason = true;
            }catch(Exception e){
                 if (topicConsumer != null) {
                    topicConsumer.onChangeSystemStatus(DataBusServiceStatus.CONFUSED);
                }
            } finally {
                lockConcurrentList.unlock();
            }

        }
        return reason;
    }

    @Override
    public <T extends Serializable> boolean unRegisterSubscriber(String subscriverId) {
        boolean reason= false;
        if (subscribers.containsKey(subscriverId)) {
            lockConcurrentList.lock();
            TopicConsumer obj = null;
            try {
                obj = subscribers.get(subscriverId);
                hzDistriuitedTopic.removeMessageListener(obj);
                subscribers.remove(subscriverId);
                obj.onChangeSystemStatus(DataBusServiceStatus.DESTROY);
                reason = true;
            } catch (Exception e) {
                if (obj != null) {
                    obj.onChangeSystemStatus(DataBusServiceStatus.CONFUSED);
                }
            } finally {
                lockConcurrentList.unlock();
            }
            
        }
        return reason;
    }
}
