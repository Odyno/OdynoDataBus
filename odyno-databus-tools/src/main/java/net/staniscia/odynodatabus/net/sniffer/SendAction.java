/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.staniscia.odynodatabus.net.sniffer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JLabel;
import javax.swing.JTextField;
import net.staniscia.odynodatabus.Publisher;
import net.staniscia.odynodatabus.exceptions.PublishException;
import net.staniscia.odynodatabus.msg.StringMessage;

public class SendAction extends AbstractAction {

    private final Publisher<String> pub;
    private final JTextField textLabel;

    SendAction(Publisher<String> dataPublisher,JTextField textLabel) {
        super("Send");
        this.pub = dataPublisher;
        this.textLabel=textLabel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
                pub.publish(new StringMessage(textLabel.getText()));
        } catch (PublishException ex) {
            Logger.getLogger(Sniffer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
