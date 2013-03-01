/*  
 Copyright 2012  Alessandro Staniscia ( alessandro@staniscia.net )

 This program is free software; you can redistribute it and/or modify
 it under the terms of the GNU General Public License, version 2, as
 published by the Free Software Foundation.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with this program; if not, write to the Free Software
 Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */
package net.staniscia.odynodatabus.net.core;

import com.hazelcast.core.ITopic;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.staniscia.odynodatabus.Publisher;
import net.staniscia.odynodatabus.exceptions.PublishException;
import net.staniscia.odynodatabus.msg.Envelop;

/**
 * Simple template.
 *
 * @param D the generic type
 * @author Alessandro Staniscia
 */
public class TopicProducer implements Publisher, Serializable {

    /**
     * The Constant LOGGER.
     */
    private static final Logger LOGGER = Logger.getLogger(TopicProducer.class.getName());
    private ITopic<Envelop> topic;

    public TopicProducer(ITopic<Envelop> topic) {
        if (topic == null) {
            throw new IllegalArgumentException("No topic FOUND");
        }
        this.topic = topic;
    }




    /* (non-Javadoc)
     * @see net.staniscia.odynodatabus.Publisher#publish(net.staniscia.odynodatabus.msg.Envelop)
     */
    @Override
    public void publish(final Envelop data) throws PublishException {
        LOGGER.log(Level.FINEST, "request publish data");
        topic.publish(data);
    }
}
