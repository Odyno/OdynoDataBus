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
package net.staniscia.odynodatabus.msg;

import java.io.Serializable;

// TODO: Auto-generated Javadoc
/**
 * Manage all object Serializable.
 *
 * @param <T> the generic type
 */
public class SerializableMessage<T extends Serializable> implements Envelop<T> {

    /** The time. */
    private long time = System.currentTimeMillis();
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 5759583007849201371L;
    
    /** The data object. */
    private T dataObject;

    /**
     * the constructor with Object to send.
     *
     * @param dataObject the data object
     */
    public SerializableMessage(T dataObject) {
        super();
        this.dataObject = dataObject;
    }

    /* (non-Javadoc)
     * @see net.staniscia.odynodatabus.msg.Envelop#getTimeOfOccurence()
     */
    @Override
    public long getTimeOfOccurence() {
        return time;
    }

    /* (non-Javadoc)
     * @see net.staniscia.odynodatabus.msg.Envelop#getContentType()
     */
    @SuppressWarnings("unchecked")
	@Override
    public Class<T> getContentType() {
        return (Class<T>) dataObject.getClass();
    }

    /* (non-Javadoc)
     * @see net.staniscia.odynodatabus.msg.Envelop#getContent()
     */
    @Override
    public T getContent() {
        return dataObject;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("SerializableMessage [");
        builder.append("getContent=");
        builder.append(getContent());
        builder.append(", getContentType=");
        builder.append(getContentType());
        builder.append(", getTimeOfOccurence=");
        builder.append(getTimeOfOccurence());
        builder.append(", dataObject=");
        builder.append(dataObject);
        builder.append(", serialVersionUID=");
        builder.append(serialVersionUID);
        builder.append(", time=");
        builder.append(time);
        builder.append(", getClass=");
        builder.append(getClass());
        builder.append(", hashCode=");
        builder.append(hashCode());
        builder.append(", toString=");
        builder.append(toString());
        builder.append("]");
        return builder.toString();
    }
}
