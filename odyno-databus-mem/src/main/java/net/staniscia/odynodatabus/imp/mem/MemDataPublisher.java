package net.staniscia.odynodatabus.imp.mem;

import java.io.Serializable;

import net.staniscia.odynodatabus.Publisher;
import net.staniscia.odynodatabus.exceptions.PublishException;
import net.staniscia.odynodatabus.msg.Envelop;

/**
 * Simple template
 * 
 * @author Alessandro Staniscia
 *
 * @param <T>
 */
public class MemDataPublisher<T extends Serializable> implements Publisher<T> {

	private MemoryDataBus  dds;

	public MemDataPublisher(MemoryDataBus dds) {
		super();
		this.dds = dds;
	}

	public void publish(Envelop<T> data) throws PublishException{
		dds.submitData(data);
	}

}
