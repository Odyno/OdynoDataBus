package net.staniscia.odynodatabus;

import net.staniscia.odynodatabus.msg.Envelop;
import java.io.Serializable;


public interface Publisher<T extends Serializable> {

	/**
	 * Publish data
	 * @param data
	 */
	void publish(Envelop<T> data);
	
	
}
