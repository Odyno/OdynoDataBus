package net.staniscia.odynodatabus.tests;

import java.sql.Time;
import java.util.Random;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import net.staniscia.odynodatabus.DataDistributionService;
import net.staniscia.odynodatabus.DataPublisher;
import net.staniscia.odynodatabus.Envelop;
import net.staniscia.odynodatabus.msg.SerializableMessage;

public class Generatore implements Runnable {
	
	private static final Logger LOG = Logger.getLogger(Generatore.class.getName()); 

	DataDistributionService service;
	Validatore vld;
	ScheduledExecutorService ses = Executors.newScheduledThreadPool(1);

	public void onStartTest() {
		LOG.info("[G] - Start gen dummy data for test");
		vld = new Validatore();
		service.registerSubscriber(vld);
		ses.scheduleAtFixedRate(this, 10, generateTic(), TimeUnit.MILLISECONDS);
		

	}

	public void onStopTest() {
		LOG.info("[G] - Stop gen dummy data for test");
		ses.shutdownNow();
		service.unRegisterSubscriber(vld);
	}

	private PersonBeanTest createInput() {
		PersonBeanTest test= new PersonBeanTest();
		test.setName("Nome-"+System.currentTimeMillis());
		test.setDeceased(System.currentTimeMillis() %2==0);
		return test;
	}

	private int generateTic() {
		Random r = new Random(System.currentTimeMillis());
		return r.nextInt(5001);
	}

	@Override
	public void run() {
		final PersonBeanTest input = createInput();
		vld.verifyObject(input);
		DataPublisher<PersonBeanTest> publicher = service
				.getDataPublisher(PersonBeanTest.class);
		SerializableMessage<PersonBeanTest> a = new SerializableMessage<PersonBeanTest>(input);
		LOG.info("[G] - Generate Data: "+a.getTimeOfOccurence());
		publicher.publish(a);
	}

}
