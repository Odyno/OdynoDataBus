package net.staniscia.odynodatabus.tests;

import net.staniscia.odynodatabus.msg.Envelop;
import net.staniscia.odynodatabus.filters.Filter;

import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import net.staniscia.odynodatabus.DataBusService;
import net.staniscia.odynodatabus.Publisher;
import net.staniscia.odynodatabus.Subscriber;
import net.staniscia.odynodatabus.msg.Envelop;

public class StubDataDistributionService implements DataBusService {

	List<Subscriber<?, ?>> listeners = Collections.synchronizedList(new LinkedList<Subscriber<?, ?>>());

	public <T extends Serializable> Publisher<T> getDataPublisher(Class<T> objectType) {

		return new Publisher<T>() {
			public void publish(Envelop<T> data) {
				fireListeners(data);
			}
		};
	}

	@SuppressWarnings("rawtypes")
	private <T extends Serializable> void fireListeners(Envelop data) {
		synchronized (listeners) {
			for (Subscriber<?, ?> listener : listeners) {
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
			Subscriber<T, ? extends Filter<T>> subscriver) {
		return listeners.add(subscriver);
	}

	public <T extends Serializable> boolean unRegisterSubscriber(
			Subscriber<T, ? extends Filter<T>> subscriver) {
		return listeners.remove(subscriver);
	}






}
