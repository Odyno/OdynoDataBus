package net.staniscia.odynodatabus;

import java.io.Serializable;

/**
 * Classe messaggero del data Event
 *
 *
 * @param <T>
 */
public interface Envelop<T extends Serializable> extends Serializable {

	/**
	 * Time in Unix Epoc of Occurence of message
	 * 
	 * @return millisecondo in UnirEpoc
	 */
	long getTimeOfOccurence();

	/**
	 * Return type of data content
	 * 
	 * @return class type
	 */
	Class<T> getContentType();

	/**
	 * Return data content 
	 * 
	 * @return data content
	 */
	T getContent();

}
