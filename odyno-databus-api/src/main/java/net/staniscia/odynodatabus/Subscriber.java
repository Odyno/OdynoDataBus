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
package net.staniscia.odynodatabus;

import net.staniscia.odynodatabus.msg.Envelop;
import java.io.Serializable;

import net.staniscia.odynodatabus.filters.Filter;

// TODO: Auto-generated Javadoc
/**
 * The subscriber of DataBus.
 *
 * @param <D> data type
 * @param <F> filter applayed on data for filtering the occurrence of data
 * @author Alessandro Staniscia
 */
public interface Subscriber<D extends Serializable, F extends Filter<D>> extends Serializable {
    
    /**
     * Get The unique ID of this Subscriber
     */
        String getIdentification();

	/**
	 * handle when the object receive message in according of filter defined in
	 * getFilter();.
	 *
	 * @param dataSample the data sample
	 */
	void handle(Envelop<D> dataSample);

	/**
	 * Return data filter of this publisher.
	 *
	 * @return the filter
	 */
	F getFilter();
        
        
        /**
         * handle when the system status is changed.
         *
         * @param status the status
         */
        void onChangeSystemStatus(final DataBusServiceStatus status);


}
