/*
 * 
 */
package net.staniscia.odynodatabus;

import net.staniscia.odynodatabus.filters.Filter;

import java.io.Serializable;




public interface DataBusService {

	<T extends Serializable> Publisher<T> getDataPublisher(Class<T> objectType);

	<T extends Serializable> boolean registerSubscriber(Subscriber<T, ? extends Filter<T>> subscriver);

	<T extends Serializable> boolean unRegisterSubscriber(Subscriber<T, ? extends Filter<T>> subscriver);
}
