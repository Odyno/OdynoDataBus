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
package net.staniscia.odynodatabus.msg;

import java.io.Serializable;

// TODO: Auto-generated Javadoc
/**
 * Classe messaggero del data Event.
 *
 * @param <T> the generic type
 */
public interface Envelop<T extends Serializable> extends Serializable {

	/**
	 * Time in Unix Epoc of Occurence of message.
	 *
	 * @return millisecondo in UnirEpoc
	 */
	long getTimeOfOccurence();

	/**
	 * Return type of data content.
	 *
	 * @return class type
	 */
	Class<T> getContentType();

	/**
	 * Return data content.
	 *
	 * @return data content
	 */
	T getContent();

}
