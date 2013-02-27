/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.staniscia.odynodatabus.net.sniffer.utils;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.staniscia.odynodatabus.Publisher;
import net.staniscia.odynodatabus.exceptions.PublishException;
import net.staniscia.odynodatabus.msg.StringMessage;

/**
 *
 * @author odyssey
 */
public class ScheduledPublicher implements Runnable {

    /**
     * The ses.
     */
    public static ScheduledExecutorService ses = Executors.newScheduledThreadPool(1);
    private Publisher<String> publi;
    private final int maxDelay;
    private final TimeUnit tUnit;

    public ScheduledPublicher(Publisher<String> publi, int maxDelay, TimeUnit tUnit) {
        this.publi = publi;
        this.maxDelay = maxDelay;
        this.tUnit = tUnit;
        scheduleMe(maxDelay, tUnit);
    }

    @Override
    public void run() {
        Logger.getLogger(ScheduledPublicher.class.getName()).log(Level.INFO, "StartMe");

        try {
            String msg = Tools.getHostName() + " send message ad " + Tools.getTime();
            Logger.getLogger(ScheduledPublicher.class.getName()).log(Level.INFO, msg);
            publi.publish(new StringMessage(msg));
            scheduleMe(maxDelay, tUnit);
        } catch (PublishException ex) {
            Logger.getLogger(ScheduledPublicher.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected void scheduleMe(int maxDelay, TimeUnit tUnit) {
        ses.schedule(this, Tools.getRandom(maxDelay), tUnit);
    }
    
    

}
