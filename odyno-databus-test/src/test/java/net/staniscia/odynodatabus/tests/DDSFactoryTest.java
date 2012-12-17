package net.staniscia.odynodatabus.tests;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;



import net.staniscia.odynodatabus.DataBusService;
import net.staniscia.odynodatabus.DataBusServiceStatus;
import net.staniscia.odynodatabus.Publisher;
import net.staniscia.odynodatabus.Subscriber;
import net.staniscia.odynodatabus.msg.Envelop;
import net.staniscia.odynodatabus.filters.Filter;
import net.staniscia.odynodatabus.imp.mem.MemoryDataBus;

import org.junit.Before;
import org.junit.Test;

public class DDSFactoryTest {

    DataBusService service = new MemoryDataBus();
    Subscriber<String, Filter<String>> strListener;
    Envelop<String> strEnvelope;
    Subscriber<Integer, Filter<Integer>> intListener;
    Envelop<Integer> intEnvelope;
    List<String> call = new ArrayList<String>();
    List<Integer> callInteger = new ArrayList<Integer>();

    @Before
    public void setUp() {
        call.clear();

        strListener = new Subscriber<String, Filter<String>>() {
            public void handle(Envelop<String> dataSample) {
                call.add(dataSample.getContent());
            }

            public Filter<String> getFilter() {
                return new Filter<String>() {
                    @Override
                    public boolean passes(String object) {
                        return true;
                    }
                };
            }

            @Override
            public void onChangeSystemStatus(DataBusServiceStatus status) {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        };



        strEnvelope = new Envelop<String>() {
            private static final long serialVersionUID = 1L;

            public long getTimeOfOccurence() {
                return 0;
            }

            public Class<String> getContentType() {
                return String.class;
            }

            public String getContent() {
                return "Pippo";
            }
        };



        // Integer
        intListener = new Subscriber<Integer, Filter<Integer>>() {
            public void handle(Envelop<Integer> dataSample) {
                callInteger.add(dataSample.getContent());
            }

            public Filter<Integer> getFilter() {
                return new Filter<Integer>() {
                    @Override
                    public boolean passes(Integer object) {
                        return true;
                    }
                };
            }

            @Override
            public void onChangeSystemStatus(DataBusServiceStatus status) {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        };

        intEnvelope = new Envelop<Integer>() {
            private static final long serialVersionUID = 1L;

            public long getTimeOfOccurence() {
                return 0;
            }

            public Class<Integer> getContentType() {
                return Integer.class;
            }

            public Integer getContent() {
                return 1;
            }
        };
    }

    @Test
    public void test() throws InterruptedException {
        Publisher<String> str = service.getDataPublisher(String.class);
        Publisher<Integer> intPublisher = service.getDataPublisher(Integer.class);

        service.registerSubscriber(strListener);
        service.registerSubscriber(intListener);

        str.publish(strEnvelope);
        intPublisher.publish(intEnvelope);

        //la sleep e' necessaria perche il publish e' asincrono.
        Thread.sleep(100);
        Assert.assertEquals(1, call.size());
        Assert.assertEquals(1, call.size());


    }
}
