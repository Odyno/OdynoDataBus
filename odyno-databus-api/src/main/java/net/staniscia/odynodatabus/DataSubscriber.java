package net.staniscia.odynodatabus;

import java.io.Serializable;

import net.staniscia.odynodatabus.filters.Filter;

/**
 * The subscriber of DataBus 
 * 
 * @author Alessandro Staniscia
 *
 * @param <D> data type
 * @param <F> filter applayed on data for filtering the occurrence of data 
 */
public interface DataSubscriber<D extends Serializable, F extends Filter<D>> {

	/**
	 * handle when the object receive message in according of filter defined in
	 * getFilter();
	 *
	 * @param dataSample
	 */
	void handle(Envelop<D> dataSample);

	/**
	 * Return data filter of this publisher
	 *
	 * @return
	 */
	F getFilter();
        
        
        /**
         *  handle when the system status is changed
         * 
         * @param status 
         */
        void onChangeSystemStatus(final DataDistributionServiceStatus status);


}
