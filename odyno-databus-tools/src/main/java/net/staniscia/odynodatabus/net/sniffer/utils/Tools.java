/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.staniscia.odynodatabus.net.sniffer.utils;

import net.staniscia.odynodatabus.net.sniffer.utils.ScheduledPublicher;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author odyssey
 */
public class Tools {

    public static long getRandom(int n) {
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
    
    public static String getHostName() {
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
                    out += " "+current_addr.getHostAddress();
                }

            }
        } catch (SocketException ex) {
            Logger.getLogger(ScheduledPublicher.class.getName()).log(Level.SEVERE, null, ex);
        }
        return out;
    }

    public static String getTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }
}
