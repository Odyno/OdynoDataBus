package net.staniscia.odynodatabus.msg;

import java.io.Serializable;

import net.staniscia.odynodatabus.Envelop;

public class SerializableMessage<T extends Serializable> implements Envelop<T> {

	private long time = System.currentTimeMillis();

	private static final long serialVersionUID = 5759583007849201371L;

	private T dataObject;

	public SerializableMessage(T dataObject) {
		super();
		this.dataObject = dataObject;
	}

	public long getTimeOfOccurence() {
		return time;
	}

	@SuppressWarnings("unchecked")
	public Class<T> getContentType() {
		return (Class<T>) dataObject.getClass();
	}

	public T getContent() {
		return dataObject;
	}

}
