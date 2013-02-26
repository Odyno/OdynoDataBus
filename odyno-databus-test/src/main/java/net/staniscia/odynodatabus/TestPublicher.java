/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.staniscia.odynodatabus;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.staniscia.odynodatabus.exceptions.PublishException;
import net.staniscia.odynodatabus.msg.StringMessage;
import net.staniscia.odynodatabus.net.DataBusCluster;

/**
 *
 * @author odyssey
 */
public class TestPublicher implements Runnable {

    /**
     * The ses.
     */
    public static ScheduledExecutorService ses = Executors.newScheduledThreadPool(1);
    private Publisher<String> publi;

    public TestPublicher(Publisher<String> publi) {
        this.publi = publi;
        ses.schedule(this, getDelay(5000), TimeUnit.MILLISECONDS);
    }

    public static  String getHostName() {
        String out = "No IP";
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {

                NetworkInterface current = interfaces.nextElement();
                out = current.toString();
                if (!current.isUp() || current.isLoopback() || current.isVirtual()) {
                    continue;
                }
                Enumeration<InetAddress> addresses = current.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    InetAddress current_addr = addresses.nextElement();
                    if (current_addr.isLoopbackAddress()) {
                        continue;
                    }
                    out += current_addr.getHostAddress();
                }

            }
        } catch (SocketException ex) {
            Logger.getLogger(TestPublicher.class.getName()).log(Level.SEVERE, null, ex);
        }
        return out;
    }

    public static String getTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }

    @Override
    public void run() {
        Logger.getLogger(TestPublicher.class.getName()).log(Level.INFO,"StartMe");
        
        try {
            String msg = getHostName() + " send message ad " + getTime();
            Logger.getLogger(TestPublicher.class.getName()).log(Level.INFO, msg);
            publi.publish(new StringMessage(msg));
            ses.schedule(this, getDelay(5000), TimeUnit.MILLISECONDS);
        } catch (PublishException ex) {
            Logger.getLogger(TestPublicher.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public long getDelay(int n) {
        final ArrayList<Long> arr = new ArrayList<Long>(n);
        for (long i = 0; i < n; i++) {
            arr.add(i);
        }
        Collections.shuffle(arr);
        for (Long val : arr) {
            return val;
        }
        return 0;
    }

    public static void main(String args[]) {
        DataBusService dbs = new DataBusCluster();
        Publisher<String> publi = dbs.getDataPublisher(String.class);
        TestPublicher tt=new TestPublicher(publi);      
    }
}
