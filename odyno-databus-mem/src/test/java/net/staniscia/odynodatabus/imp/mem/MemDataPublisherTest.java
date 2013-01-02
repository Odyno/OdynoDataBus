/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.staniscia.odynodatabus.imp.mem;

import java.io.Serializable;

import net.staniscia.odynodatabus.DataBusService;
import net.staniscia.odynodatabus.exceptions.PublishException;
import net.staniscia.odynodatabus.msg.Envelop;
import net.staniscia.odynodatabus.msg.StringMessage;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 *
 * @author Alessandro Staniscia
 */
public class MemDataPublisherTest {
    
    public MemDataPublisherTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of publish method, of class MemDataPublisher.
     * @throws PublishException 
     */
    @Test
    public void testPublishNullObject() throws PublishException {
        MemoryDataBus dds = mock(MemoryDataBus.class);
        MemDataPublisher instance = new MemDataPublisher<Serializable>(dds);
        instance.publish(null);
    }
    
    
    /**
     * Test of publish method, of class MemDataPublisher.
     * @throws PublishException 
     */
    @Test
    public void testPublishObject() throws PublishException {
    	
        MemoryDataBus dds = mock(MemoryDataBus.class);
        MemDataPublisher instance = new MemDataPublisher<String>(dds);
		instance.publish(new StringMessage("ciao Come VA?"));
    }
    
    /**
     * Test of publish method, of class MemDataPublisher.
     * @throws PublishException 
     */
    @Test (expected=PublishException.class)
    public void testPublishObjectReturnException() throws PublishException {
    	MemoryDataBus dds = mock(MemoryDataBus.class);
    	
        StringMessage dummy = new StringMessage("ciao Come VA?");
        doThrow(PublishException.class).when(dds).submitData(any(StringMessage.class));
        MemDataPublisher<String> instance = new MemDataPublisher<String>(dds);
		instance.publish(dummy);

    }
    
}
