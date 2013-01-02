/**
 * 
 */
package net.staniscia.odynodatabus.exceptions;

/**
 * @author Alessandro Staniscia
 * 
 */
public class PublishException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8203758289602128144L;

	/**
	 * 
	 */
	public PublishException() {
		super();
	}

	/**
	 * @param message
	 */
	public PublishException(final String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param cause
	 */
	public PublishException(final Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 */
	public PublishException(final String message, final Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

}
