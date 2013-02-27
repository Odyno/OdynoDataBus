/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.staniscia.odynodatabus.net.sniffer.utils;

import net.staniscia.odynodatabus.DataBusServiceStatus;
import net.staniscia.odynodatabus.Subscriber;
import net.staniscia.odynodatabus.filters.Filter;
import net.staniscia.odynodatabus.filters.FilterFactory;
import net.staniscia.odynodatabus.msg.Envelop;


/**
 *
 * @author odyssey
 */
public class SimpleConsumer implements Subscriber<String, Filter<String>> {

    @Override
    public String getIdentification() {
        return Tools.getHostName() + " " + getClass().getName();
    }

    @Override
    public void handle(Envelop<String> dataSample) {
        System.out.println(Tools.getTime() + "-Content: " + dataSample.getContent());
    }

    @Override
    public Filter<String> getFilter() {
        return FilterFactory.makeNoFilter("");
    }

    @Override
    public void onChangeSystemStatus(DataBusServiceStatus status) {
        System.out.println(Tools.getTime() + "-Content: " + status.toString());
    }

}
