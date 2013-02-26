package net.staniscia.odynodatabus.imp.mem;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import net.staniscia.odynodatabus.Subscriber;
import net.staniscia.odynodatabus.filters.Filter;

import org.junit.Test;

public class MemoryDataBusTest {

	@Test
	public void testGetDataPublisher() {
		MemoryDataBus mdb=new MemoryDataBus();
		assertNotNull(mdb.getDataPublisher(String.class));
		assertNotNull(mdb.getDataPublisher(Long.class));
		assertNotNull(mdb.getDataPublisher(Boolean.class));
		assertNotNull(mdb.getDataPublisher(ArrayList.class));
		assertNotNull(mdb.getDataPublisher(Vector.class));
		assertNotNull(mdb.getDataPublisher(HashMap.class));
	}

	@Test
	public void testRegisterSubscriber() {
		MemoryDataBus mdb=new MemoryDataBus();
		Subscriber<Serializable, ? extends Filter<Serializable>> subscriver=mock(Subscriber.class);
                when(subscriver.getIdentification()).thenReturn("123");
		mdb.registerSubscriber(subscriver);
		assertFalse(mdb.unRegisterSubscriber("subscriver"));
	}

        
	@Test
	public void testRegisterSubscriber2() {
		MemoryDataBus mdb=new MemoryDataBus();
		Subscriber<Serializable, ? extends Filter<Serializable>> subscriver=mock(Subscriber.class);
                when(subscriver.getIdentification()).thenReturn("123");
		mdb.registerSubscriber(subscriver);
		assertTrue(mdb.unRegisterSubscriber("123"));
	}

}
