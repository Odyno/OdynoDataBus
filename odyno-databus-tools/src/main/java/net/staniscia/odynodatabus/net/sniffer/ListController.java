/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.staniscia.odynodatabus.net.sniffer;

import java.io.Serializable;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import net.staniscia.odynodatabus.DataBusServiceStatus;
import net.staniscia.odynodatabus.Subscriber;
import net.staniscia.odynodatabus.filters.Filter;
import net.staniscia.odynodatabus.msg.Envelop;
import net.staniscia.odynodatabus.net.sniffer.utils.Tools;

/**
 *
 * @author odyssey
 */
public class ListController implements Subscriber<Serializable, Filter<Serializable>> {

    private JList view;
    private final DefaultListModel model;

    public ListController(JList receiverList) {
        this.view = receiverList;
        model = new DefaultListModel();
        view.setModel(model);

    }

    @Override
    public String getIdentification() {
        return Tools.getHostName() + " " + getClass().getName();
    }

    @Override
    public void onChangeSystemStatus(DataBusServiceStatus status) {
        model.addElement(Tools.getTime() + "-Content: " + status.toString());
    }

    @Override
    public void handle(Envelop<Serializable> dataSample) {
        model.addElement(Tools.getTime() + "-Content: " + dataSample.getContent().toString());
    }

    @Override
    public Filter<Serializable> getFilter() {
        return new Filter<Serializable>() {
            @Override
            public boolean passes(Serializable object) {
                return true;
            }
        };
    }
}
