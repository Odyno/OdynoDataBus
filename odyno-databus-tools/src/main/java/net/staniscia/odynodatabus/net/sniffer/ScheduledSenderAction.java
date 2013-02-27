/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.staniscia.odynodatabus.net.sniffer;

import java.awt.event.ActionEvent;
import java.util.concurrent.TimeUnit;
import javax.swing.AbstractAction;
import net.staniscia.odynodatabus.Publisher;
import net.staniscia.odynodatabus.net.sniffer.utils.ScheduledPublicher;

/**
 *
 * @author odyssey
 */
public class ScheduledSenderAction extends AbstractAction {

    private final Publisher<String> pub;
    private final int time;
    private  ScheduledPublicher rsa;

    ScheduledSenderAction(Publisher<String> dataPublisher,int time) {
        super("Scheduled Send "+time+"s");
        this.pub = dataPublisher;
        this.time=time;
        
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
             rsa=new ScheduledPublicher(pub, time,TimeUnit.SECONDS);
    }
    
}
