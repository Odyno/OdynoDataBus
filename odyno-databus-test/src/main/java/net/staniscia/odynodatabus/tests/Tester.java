package net.staniscia.odynodatabus.tests;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import net.staniscia.odynodatabus.DataBusService;
import net.staniscia.odynodatabus.Publisher;
import net.staniscia.odynodatabus.exceptions.PublishException;
import net.staniscia.odynodatabus.msg.StringMessage;

public class Tester implements Runnable {

	private ScheduledExecutorService ses = Executors.newScheduledThreadPool(1);
	private DataBusService service;
	private Validatore vld=new Validatore();

	public Tester() {
		super();
	}

	public void startMe() {
		ses.scheduleAtFixedRate(this, 10, generateTic(), TimeUnit.MILLISECONDS);
	}
	
	public void stopMe() {
		
	}

	@Override
	public void run() {

		try {
			Publisher<String> publisher = service.getDataPublisher(String.class);
			String msg = "MSG=" + System.currentTimeMillis();
			vld.verifyObject(msg);
			publisher.publish(new StringMessage(msg));
		} catch (PublishException e) {
			e.printStackTrace();
		}

	}

	private int generateTic() {
		Random r = new Random(System.currentTimeMillis());
		return r.nextInt(5001);
	}

}
