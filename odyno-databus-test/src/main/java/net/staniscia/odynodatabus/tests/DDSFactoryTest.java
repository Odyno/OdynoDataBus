package net.staniscia.odynodatabus.tests;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;



import net.staniscia.odynodatabus.DataDistributionService;
import net.staniscia.odynodatabus.DataPublisher;
import net.staniscia.odynodatabus.DataSubscriber;
import net.staniscia.odynodatabus.Envelop;
import net.staniscia.odynodatabus.filters.Filter;
import net.staniscia.odynodatabus.imp.mem.MemoryDDS;

import org.junit.Before;
import org.junit.Test;

public class DDSFactoryTest {

	DataDistributionService service = new MemoryDDS();
	DataSubscriber<String, Filter<String>> strListener;
	Envelop<String> strEnvelope;

	DataSubscriber<Integer, Filter<Integer>> intListener;
	Envelop<Integer> intEnvelope;

	List<String> call = new ArrayList<String>();
	List<Integer> callInteger = new ArrayList<Integer>();


	@Before
	public void setUp() {
		call.clear();

		strListener = new DataSubscriber<String, Filter<String>>() {

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
		intListener = new DataSubscriber<Integer, Filter<Integer>>() {

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
		DataPublisher<String> str = service.getDataPublisher(String.class);
		DataPublisher<Integer> intPublisher = service.getDataPublisher(Integer.class);

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
