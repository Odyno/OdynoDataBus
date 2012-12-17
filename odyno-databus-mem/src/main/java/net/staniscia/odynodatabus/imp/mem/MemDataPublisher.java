package net.staniscia.odynodatabus.imp.mem;

import java.io.Serializable;

import net.staniscia.odynodatabus.DataPublisher;
import net.staniscia.odynodatabus.Envelop;

/**
 * Simple template
 * 
 * @author Alessandro Staniscia
 *
 * @param <T>
 */
public class MemDataPublisher<T extends Serializable> implements DataPublisher<T> {

	private MemoryDDS  dds;

	public MemDataPublisher(MemoryDDS dds) {
		super();
		this.dds = dds;
	}

	public void publish(Envelop<T> data) {
		dds.notifyData(data);
	}

}
