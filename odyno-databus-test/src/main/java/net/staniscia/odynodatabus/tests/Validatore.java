package net.staniscia.odynodatabus.tests;

import java.util.Collections;
import java.util.Vector;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.logging.Logger;

import net.staniscia.odynodatabus.DataSubscriber;
import net.staniscia.odynodatabus.Envelop;
import net.staniscia.odynodatabus.filters.Filter;
import net.staniscia.odynodatabus.filters.FilterFactory;

public class Validatore implements DataSubscriber<PersonBeanTest, Filter<PersonBeanTest>> {
	private static final Logger LOG = Logger.getLogger(Validatore.class.getName()); 

	
	private ArrayBlockingQueue<PersonBeanTest> buffer=new ArrayBlockingQueue<PersonBeanTest>(1000);

	public void verifyObject(PersonBeanTest input) {
        buffer.offer(input)		;
	}

	@Override
	public void handle(Envelop<PersonBeanTest> dataSample) {
		PersonBeanTest obj = dataSample.getContent();
		PersonBeanTest offer = buffer.poll();
		if (obj.equals(offer)){
			LOG.fine("[V] - MSG "+dataSample.getTimeOfOccurence() + " is ok!");
		}else{
			LOG.warning("[ERROR] - Aspettavo "+offer+" ho ricevuto "+obj);
		}
	}

	@Override
	public Filter<PersonBeanTest> getFilter() {
		return FilterFactory.makeNoFilter(new PersonBeanTest());
	}

}
