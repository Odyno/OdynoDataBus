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

import java.util.concurrent.ArrayBlockingQueue;
import java.util.logging.Logger;
import net.staniscia.odynodatabus.DataBusServiceStatus;

import net.staniscia.odynodatabus.Subscriber;
import net.staniscia.odynodatabus.msg.Envelop;
import net.staniscia.odynodatabus.filters.Filter;
import net.staniscia.odynodatabus.filters.FilterFactory;

// TODO: Auto-generated Javadoc
/**
 * The Class Validatore.
 */
public class Validatore implements Subscriber<String, Filter<String>> {
	
	/** The Constant LOG. */
	private static final Logger LOG = Logger.getLogger(Validatore.class.getName()); 

	
	/** The buffer. */
	private ArrayBlockingQueue<String> buffer=new ArrayBlockingQueue<String>(1000);

	/**
	 * Verify object.
	 *
	 * @param input the input
	 */
	public void verifyObject(String input) {
        buffer.offer(input)		;
	}

	/* (non-Javadoc)
	 * @see net.staniscia.odynodatabus.Subscriber#handle(net.staniscia.odynodatabus.msg.Envelop)
	 */
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

	/* (non-Javadoc)
	 * @see net.staniscia.odynodatabus.Subscriber#getFilter()
	 */
	@Override
	public Filter<String> getFilter() {
		return FilterFactory.makeNoFilter("");
	}

    /* (non-Javadoc)
     * @see net.staniscia.odynodatabus.Subscriber#onChangeSystemStatus(net.staniscia.odynodatabus.DataBusServiceStatus)
     */
    @Override
    public void onChangeSystemStatus(DataBusServiceStatus status) {
    	LOG.fine("[V] - Status is "+status);
    }

}
