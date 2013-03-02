/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.staniscia.odynodatabus.net.core;

import com.hazelcast.core.Message;
import com.hazelcast.core.MessageListener;
import java.io.Serializable;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import net.staniscia.odynodatabus.DataBusServiceStatus;
import net.staniscia.odynodatabus.Subscriber;
import net.staniscia.odynodatabus.filters.Filter;
import net.staniscia.odynodatabus.msg.Envelop;

/**
 *
 *
 */
public class TopicConsumer<D extends Serializable> implements MessageListener<Envelop<D>>, Serializable {

    //private static final Executor messageExecutor = Executors.newSingleThreadExecutor();
    private Subscriber<D, Filter<D>> subscriver;

    public TopicConsumer(Subscriber<D, Filter<D>> subscriver) {
        this.subscriver = subscriver;
        onChangeSystemStatus(DataBusServiceStatus.BOOTING);
    }

    @Override
    public void onMessage(final Message<Envelop<D>> msg) {
          notifyToSubscriber(msg.getMessageObject());
//        messageExecutor.execute(new Runnable() {
//            @Override
//            public void run() {
//              
//            }
//        });
    }

    private void notifyToSubscriber(Envelop<D> messageObject) {
        final Filter f = subscriver.getFilter();
        if (f.getManagedType() == null
                || messageObject.getContentType().isAssignableFrom(f.getManagedType())) {
            Serializable content = messageObject.getContent();
            if (f.passes(content)) {
                subscriver.handle(messageObject);
            }
        }
    }

    /**
     * handle when the system status is changed.
     *
     * @param status the status
     */
    public void onChangeSystemStatus(final DataBusServiceStatus status) {
        subscriver.onChangeSystemStatus(status);
//        messageExecutor.execute(new Runnable() {
//            @Override
//            public void run() {
//                
//            }
//        });
    }
}
