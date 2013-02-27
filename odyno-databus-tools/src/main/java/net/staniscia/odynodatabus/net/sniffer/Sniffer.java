/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.staniscia.odynodatabus.net.sniffer;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import net.staniscia.odynodatabus.DataBusService;
import net.staniscia.odynodatabus.Publisher;
import net.staniscia.odynodatabus.net.sniffer.ui.SnifferUI;

public class Sniffer {

    private static Logger LOG = Logger.getLogger(Sniffer.class.getName());
    public final static String NAME = "Sniffer";
    private DataBusService dataBusService;
    private ListController listCtr;
    private SendAction sendAction;
    private SnifferUI ui;

    public Sniffer(DataBusService dataBusService) {
        if (dataBusService == null) {
            throw new IllegalArgumentException("NULL SERVICE");
        }
        setDBS(dataBusService);
    }

    public void setDataBusService(DataBusService dataBusService) {
        setDBS(dataBusService);
    }

    private void setDBS(DataBusService dataBusService) {
        if (dataBusService != null) {
            LOG.log(Level.INFO, "Inject ServiceDataBus");
            this.dataBusService = dataBusService;
            this.dataBusService.registerSubscriber(getListCtrl());
            Publisher<String> pub = dataBusService.getDataPublisher(String.class);
            sendAction = new SendAction(pub, getUi().messageLabel);
            getUi().sendButton.addActionListener(sendAction);
            getUi().sendButton.setEnabled(true);
            getUi().Menu.add(new JMenuItem(new  RandomSenderAction(pub, 5)));
            getUi().Menu.add(new JMenuItem(new  ScheduledSenderAction(pub, 5)));
        }
    }

    public void start() {
        LOG.log(Level.INFO, "Start The SNIFFER");
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                getUi().setVisible(true);
            }
        });
    }

    public void stop() {
        LOG.log(Level.INFO, "Stop The SNIFFER");
        LOG.log(Level.INFO, "unRegisterSubscriber SNIFFER");
        dataBusService.unRegisterSubscriber(getListCtrl().getIdentification());
        LOG.log(Level.INFO, "unRegisterSubscriber SNIFFER");
        sendAction=null;
        getUi().dispose();
    }

    private SnifferUI getUi() {
        if (this.ui == null) {
            ui = new SnifferUI();
            ui.Menu.add(new JMenuItem(new CloseAction(ui)));
        }
        return ui;
    }

    private ListController getListCtrl() {
        if (listCtr == null) {
            listCtr = new ListController(getUi().receiverList);
        }
        return listCtr;
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


        Sniffer sniff = new Sniffer(null);
        JOptionPane.showMessageDialog(sniff.getUi(), "This is a Dummy pannel, to use it deploy in OSGi Container!");
        sniff.start();
    }
}
