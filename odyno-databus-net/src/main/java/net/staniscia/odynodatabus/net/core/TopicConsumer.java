/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.staniscia.odynodatabus.net.core;

import com.hazelcast.core.Message;
import com.hazelcast.core.MessageListener;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import net.staniscia.odynodatabus.Subscriber;
import net.staniscia.odynodatabus.filters.Filter;
import net.staniscia.odynodatabus.msg.Envelop;

/**
 *
 *
 */
public class TopicConsumer implements MessageListener<Envelop<?>> {

    private static final Executor messageExecutor = Executors.newSingleThreadExecutor();
    private Subscriber<?, ? extends Filter<?>> subscriver;

    public TopicConsumer(Subscriber<?, ? extends Filter<?>> subscriver) {
        this.subscriver = subscriver;
    }

    @Override
    public void onMessage(final Message<Envelop<?>> msg) {
        messageExecutor.execute(new Runnable() {
            @Override
            public void run() {
                Envelop data = msg.getMessageObject();
                notifyToSubscriber(data);
            }
        });
    }

    private void notifyToSubscriber(Envelop data) {
        final Filter f = subscriver.getFilter();
        if (data.getContentType().isAssignableFrom(f.getManagedType())) {
            Serializable content = data.getContent();
            if (f.passes(content)) {
                subscriver.handle(data);
            }
        }
    }
}
