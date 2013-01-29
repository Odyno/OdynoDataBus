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
package net.staniscia.odynodatabus.exceptions;

// TODO: Auto-generated Javadoc
/**
 * The Class PublishException.
 *
 * @author Alessandro Staniscia
 */
public class PublishException extends Exception {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 8203758289602128144L;

	/**
	 * Instantiates a new publish exception.
	 */
	public PublishException() {
		super();
	}

	/**
	 * Instantiates a new publish exception.
	 *
	 * @param message the message
	 */
	public PublishException(final String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Instantiates a new publish exception.
	 *
	 * @param cause the cause
	 */
	public PublishException(final Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Instantiates a new publish exception.
	 *
	 * @param message the message
	 * @param cause the cause
	 */
	public PublishException(final String message, final Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

}
