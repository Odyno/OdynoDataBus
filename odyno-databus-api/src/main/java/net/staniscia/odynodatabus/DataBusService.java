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

import net.staniscia.odynodatabus.filters.Filter;

import java.io.Serializable;




// TODO: Auto-generated Javadoc
/**
 * The Interface DataBusService.
 */
public interface DataBusService {

	/**
	 * Gets the data publisher.
	 *
	 * @param <T> the generic type
	 * @param objectType the object type
	 * @return the data publisher
	 */
	<T extends Serializable> Publisher<T> getDataPublisher(Class<T> objectType);

	/**
	 * Register subscriber.
	 *
	 * @param <T> the generic type
	 * @param subscriver the subscriver
	 * @return Object, the handle of Subscriber
	 */
	<T extends Serializable> boolean registerSubscriber(Subscriber<T, ? extends Filter<T>> subscriver);

	/**
	 * Un register subscriber.
	 *
	 * @param <T> the generic type
	 * @param subscriver the subscriver
	 * @return true, if successful
	 */
	<T extends Serializable> boolean unRegisterSubscriber(String subscriverId);
}
