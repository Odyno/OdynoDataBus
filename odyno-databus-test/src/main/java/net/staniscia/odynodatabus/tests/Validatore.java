package net.staniscia.odynodatabus.tests;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.logging.Logger;
import net.staniscia.odynodatabus.DataBusServiceStatus;

import net.staniscia.odynodatabus.Subscriber;
import net.staniscia.odynodatabus.msg.Envelop;
import net.staniscia.odynodatabus.filters.Filter;
import net.staniscia.odynodatabus.filters.FilterFactory;

public class Validatore implements Subscriber<String, Filter<String>> {
	private static final Logger LOG = Logger.getLogger(Validatore.class.getName()); 

	
	private ArrayBlockingQueue<String> buffer=new ArrayBlockingQueue<String>(1000);

	public void verifyObject(String input) {
        buffer.offer(input)		;
	}

	@Override
	public void handle(Envelop<String> dataSample) {
		String obj = dataSample.getContent();
		String offer = buffer.poll();
		if (obj.equals(offer)){
			LOG.fine("[V] - "+obj+" is ok!");
		}else{
			LOG.warning("[ERROR] - Aspettavo "+offer+" ho ricevuto "+obj);
		}
	}

	@Override
	public Filter<String> getFilter() {
		return FilterFactory.makeNoFilter("");
	}

    @Override
    public void onChangeSystemStatus(DataBusServiceStatus status) {
    	LOG.fine("[V] - Status is "+status);
    }

}
