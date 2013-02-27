/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.staniscia.odynodatabus.net.sniffer.utils;

import java.util.concurrent.TimeUnit;
import net.staniscia.odynodatabus.Publisher;

/**
 *
 * @author odyssey
 */
public class RandomScheduledPublischer extends ScheduledPublicher {

    public RandomScheduledPublischer(Publisher<String> publi, int maxDelay, TimeUnit tUnit) {
        super(publi, maxDelay, tUnit);

    }

    @Override
    protected void scheduleMe(int maxDelay, TimeUnit tUnit) {
        ses.schedule(this, Tools.getRandom(maxDelay), tUnit);
    }
}
