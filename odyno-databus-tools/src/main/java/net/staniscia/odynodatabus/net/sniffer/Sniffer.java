/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.staniscia.odynodatabus.net.sniffer;

import javax.swing.JOptionPane;
import net.staniscia.odynodatabus.DataBusService;
import net.staniscia.odynodatabus.net.sniffer.ui.SnifferUI;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(name="Sniffer")        
public class Sniffer {

    private DataBusService dbs;
    private ListController listCtr;
    private SendAction sendAction;
    private SnifferUI ui;


    @Reference
    public void setDataBusService(DataBusService dbs) {
        this.dbs = dbs;
        initConsumer();
        initPublischer();

    }

    @Activate
    public void start() {
        initHci();
        showUi();
    }

    private SnifferUI getUi() {
        if (this.ui == null) {
            ui = new SnifferUI();
        }
        return ui;
    }

    private void initConsumer() {
        dbs.registerSubscriber(listCtr);
    }

    private void initPublischer() {
        sendAction = new SendAction(dbs.getDataPublisher(String.class), getUi().messageLabel);
        getUi().sendButton.addActionListener(sendAction);
        getUi().sendButton.setEnabled(true);

    }

    private void showUi() {
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                getUi().setVisible(true);
            }
        });
    }

    private void initHci() {
        getUi();
        listCtr = new ListController(getUi().receiverList);
        getUi().sendButton.setEnabled(false);
    }

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(SnifferUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SnifferUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SnifferUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SnifferUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }


        Sniffer sniff = new Sniffer();
        JOptionPane.showMessageDialog(sniff.getUi(), "This is a Dummy pannel, to use it deploy in OSGi Container!");
        sniff.start();
    }
}
