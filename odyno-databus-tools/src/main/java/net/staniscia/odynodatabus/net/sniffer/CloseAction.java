/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.staniscia.odynodatabus.net.sniffer;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;

/**
 *
 * @author odyssey
 */
public class CloseAction extends AbstractAction{

    public CloseAction() {
        super("Exit");
    }

    
    @Override
    public void actionPerformed(ActionEvent e) {
        System.exit(0);
    }
    
}
