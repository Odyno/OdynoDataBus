/*  
    Copyright 2012  Alessandro Staniscia ( alessandro@staniscia.net )

    This program is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License, version 2, as
    published by the Free Software Foundation.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
*/
package net.staniscia.odynodatabus.tests;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.staniscia.odynodatabus.DataBusService;
import net.staniscia.odynodatabus.Publisher;
import net.staniscia.odynodatabus.exceptions.PublishException;
import net.staniscia.odynodatabus.msg.StringMessage;

// TODO: Auto-generated Javadoc
/**
 * The Class Tester.
 */
public class Tester implements Runnable {
	
	/** The Constant LOG. */
	private static final Logger LOG = Logger.getLogger(Tester.class.getName()); 


	/** The ses. */
	private ScheduledExecutorService ses = Executors.newScheduledThreadPool(1);
	
	/** The service. */
	private DataBusService service;
	
	/** The vld. */
	private Validatore vld=new Validatore();

	/**
	 * Instantiates a new tester.
	 */
	public Tester() {
		super();
		LOG.log(Level.INFO, "Run OdynoDatabus TESTER!");
	}

	/**
	 * Start me.
	 */
	public void startMe() {
		LOG.log(Level.INFO, "STARTED OdynoDatabus TESTER!");
		ses.scheduleAtFixedRate(this, 10, generateTic(), TimeUnit.MILLISECONDS);
	}
	
	/**
	 * Stop me.
	 */
	public void stopMe() {
		LOG.log(Level.INFO, "STOPPED OdynoDatabus TESTER!");
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
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

	/**
	 * Generate tic.
	 *
	 * @return the int
	 */
	private int generateTic() {
		Random r = new Random(System.currentTimeMillis());
		return r.nextInt(5001);
	}

}
