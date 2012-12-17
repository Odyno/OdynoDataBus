package net.staniscia.odynodatabus;

import java.io.Serializable;


public interface DataPublisher<T extends Serializable> {

	/**
	 * Publish data
	 * @param data
	 */
	void publish(Envelop<T> data);
	
	
}
