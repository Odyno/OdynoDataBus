/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.staniscia.odynodatabus;

import net.staniscia.odynodatabus.filters.Filter;
import net.staniscia.odynodatabus.filters.FilterFactory;
import net.staniscia.odynodatabus.msg.Envelop;
import net.staniscia.odynodatabus.net.DataBusCluster;

/**
 *
 * @author odyssey
 */
public class TestConsumer implements Subscriber<String, Filter<String>> {

    @Override
    public String getIdentification() {
        return TestPublicher.getHostName() + " " + getClass().getName();
    }

    @Override
    public void handle(Envelop<String> dataSample) {
        System.out.println(TestPublicher.getTime() + "-Content: " + dataSample.getContent());
    }

    @Override
    public Filter<String> getFilter() {
        return FilterFactory.makeNoFilter("");
    }

    @Override
    public void onChangeSystemStatus(DataBusServiceStatus status) {
        System.out.println(TestPublicher.getTime() + "-Content: " + status.toString());
    }

    public static void main(String args[]) {
        DataBusService dbs = new DataBusCluster();
        dbs.registerSubscriber(new TestConsumer());
    }
}
