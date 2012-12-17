package net.staniscia.odynodatabus.tests;

import net.staniscia.odynodatabus.DataBusService;
import net.staniscia.odynodatabus.DataBusServiceStatus;
import net.staniscia.odynodatabus.Publisher;
import net.staniscia.odynodatabus.Subscriber;
import net.staniscia.odynodatabus.msg.Envelop;
import net.staniscia.odynodatabus.msg.Envelop;
import net.staniscia.odynodatabus.filters.Filter;
import net.staniscia.odynodatabus.tests.StubDataDistributionService;

import org.junit.Test;


public class DataBusTestCase {

	DataBusService dataService = new StubDataDistributionService();

	Subscriber<String, Filter<String>> listener = new Subscriber<String, Filter<String>>() {

		public void handle(Envelop<String> dataSample) {
			System.out.println("DataBusTestCase.enclosing_method() " + dataSample.getContent());
		}

		@SuppressWarnings("unchecked")
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
            System.out.println("Status changed: "+status.toString());
        }
	};



	@Test
	public void testDataBus() throws Exception {
		dataService.registerSubscriber(listener);

		Publisher<String> str = dataService.getDataPublisher(String.class);

		str.publish(new Envelop<String>() {

			public long getTimeOfOccurence() {
				return 0;
			}

			public Class<String> getContentType() {
				return String.class;
			}

			public String getContent() {
				return "Pippo";
			}
		});


		Publisher<Integer> intPublisher = dataService.getDataPublisher(Integer.class);

		intPublisher.publish(new Envelop<Integer>() {

			public long getTimeOfOccurence() {
				return 0;
			}

			public Class<Integer> getContentType() {
				return Integer.class;
			}

			public Integer getContent() {
				return 1;
			}
		});
	}

}
