/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.staniscia.odynodatabus.net.sniffer;

import java.awt.event.ActionEvent;
import java.util.concurrent.TimeUnit;
import javax.swing.AbstractAction;
import net.staniscia.odynodatabus.Publisher;
import net.staniscia.odynodatabus.net.sniffer.utils.RandomScheduledPublischer;

/**
 *
 * @author odyssey
 */
public class RandomSenderAction extends AbstractAction {

    private final Publisher pub;
    private final int time;
    private  RandomScheduledPublischer rsa;

    RandomSenderAction(Publisher dataPublisher,int time) {
        super("Rundom Send "+time+"s");
        this.pub = dataPublisher;
        this.time=time;
        
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
             rsa=new RandomScheduledPublischer(pub, time,TimeUnit.SECONDS);
    }
    
}
