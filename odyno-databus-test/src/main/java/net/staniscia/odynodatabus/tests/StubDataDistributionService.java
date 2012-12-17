package net.staniscia.odynodatabus.tests;

import net.staniscia.odynodatabus.Envelop;
import net.staniscia.odynodatabus.filters.Filter;

import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import net.staniscia.odynodatabus.DataDistributionService;
import net.staniscia.odynodatabus.DataPublisher;
import net.staniscia.odynodatabus.DataSubscriber;
import net.staniscia.odynodatabus.Envelop;

public class StubDataDistributionService implements DataDistributionService {

	List<DataSubscriber<?, ?>> listeners = Collections.synchronizedList(new LinkedList<DataSubscriber<?, ?>>());

	public <T extends Serializable> DataPublisher<T> getDataPublisher(Class<T> objectType) {

		return new DataPublisher<T>() {
			public void publish(Envelop<T> data) {
				fireListeners(data);
			}
		};
	}

	@SuppressWarnings("rawtypes")
	private <T extends Serializable> void fireListeners(Envelop data) {
		synchronized (listeners) {
			for (DataSubscriber<?, ?> listener : listeners) {
				Filter f = listener.getFilter();
				if (data.getContentType().isAssignableFrom(f.getManagedType()) ) {
					if (f.passes(data.getContent())) {
						listener.handle(data);
					}
				}
			}
		}
	}

	public <T extends Serializable> boolean registerSubscriber(
			DataSubscriber<T, ? extends Filter<T>> subscriver) {
		return listeners.add(subscriver);
	}

	public <T extends Serializable> boolean unRegisterSubscriber(
			DataSubscriber<T, ? extends Filter<T>> subscriver) {
		return listeners.remove(subscriver);
	}






}
