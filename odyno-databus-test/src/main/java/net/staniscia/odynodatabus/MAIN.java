/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.staniscia.odynodatabus;

import net.staniscia.odynodatabus.net.DataBusCluster;

/**
 *
 * @author odyssey
 */
public class MAIN {

    public static void main(String args[]) {
        DataBusService dbs = new DataBusCluster();
        if (args[0] == "p") {
            Publisher(dbs);
        } else {
            SubScriber(dbs);
        }
    }

    private static void Publisher(DataBusService dbs) {
        Publisher<String> publi = dbs.getDataPublisher(String.class);
        TestPublicher tt = new TestPublicher(publi);
    }

    private static void SubScriber(DataBusService dbs) {
        dbs.registerSubscriber(new TestConsumer());
    }
}
