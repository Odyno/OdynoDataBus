package net.staniscia.odynodatabus.tests;

import net.staniscia.odynodatabus.DataDistributionService;
import net.staniscia.odynodatabus.DataDistributionServiceStatus;
import net.staniscia.odynodatabus.DataPublisher;
import net.staniscia.odynodatabus.DataSubscriber;
import net.staniscia.odynodatabus.Envelop;
import net.staniscia.odynodatabus.Envelop;
import net.staniscia.odynodatabus.filters.Filter;

import org.junit.Test;


public class DataBusTestCase {

	DataDistributionService dataService = new StubDataDistributionService();

	DataSubscriber<String, Filter<String>> listener = new DataSubscriber<String, Filter<String>>() {

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
        public void onChangeSystemStatus(DataDistributionServiceStatus status) {
            System.out.println("Status changed: "+status.toString());
        }
	};



	@Test
	public void testDataBus() throws Exception {
		dataService.registerSubscriber(listener);

		DataPublisher<String> str = dataService.getDataPublisher(String.class);

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


		DataPublisher<Integer> intPublisher = dataService.getDataPublisher(Integer.class);

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
