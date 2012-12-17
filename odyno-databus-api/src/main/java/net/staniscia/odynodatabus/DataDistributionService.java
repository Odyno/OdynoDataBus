package net.staniscia.odynodatabus;

import net.staniscia.odynodatabus.filters.Filter;

import java.io.Serializable;




public interface DataDistributionService {

	<T extends Serializable> DataPublisher<T> getDataPublisher(Class<T> objectType);

	<T extends Serializable> boolean registerSubscriber(DataSubscriber<T, ? extends Filter<T>> subscriver);

	<T extends Serializable> boolean unRegisterSubscriber(DataSubscriber<T, ? extends Filter<T>> subscriver);
}
