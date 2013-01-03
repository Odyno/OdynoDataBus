package net.staniscia.odynodatabus.msg;

import java.io.Serializable;

/**
 * Manage all object Serializable
 *
 * @param <T>
 */
public class SerializableMessage<T extends Serializable> implements Envelop<T> {

    private long time = System.currentTimeMillis();
    private static final long serialVersionUID = 5759583007849201371L;
    private T dataObject;

    /**
     * the constructor with Object to send
     *
     * @param dataObject
     */
    public SerializableMessage(T dataObject) {
        super();
        this.dataObject = dataObject;
    }

    @Override
    public long getTimeOfOccurence() {
        return time;
    }

    @SuppressWarnings("unchecked")
	@Override
    public Class<T> getContentType() {
        return (Class<T>) dataObject.getClass();
    }

    @Override
    public T getContent() {
        return dataObject;
    }

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
