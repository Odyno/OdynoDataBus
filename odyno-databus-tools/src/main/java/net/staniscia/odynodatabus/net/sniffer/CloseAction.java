/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.staniscia.odynodatabus.net.sniffer;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import net.staniscia.odynodatabus.net.sniffer.ui.SnifferUI;

/**
 *
 * @author odyssey
 */
public class CloseAction extends AbstractAction{
    private final SnifferUI ui;



    CloseAction(SnifferUI ui) {
        super("Exit");
        this.ui=ui;
    }

    
    @Override
    public void actionPerformed(ActionEvent e) {
        ui.dispose();
    }
    
}
