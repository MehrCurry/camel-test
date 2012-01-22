package de.gzockoll.prototype.camel.encashment.service;

import static org.hamcrest.core.Is.*;
import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ThroughputMeasurmentProcessorTest {
	private ThroughputMeasurmentProcessor bus;

	@Before
	public void setUp() {
		bus = new ThroughputMeasurmentProcessor(1000);
	}

	@After
	public void tearDown() {
		bus.stop();
	}

	@Test
	public void testAdd() {
		assertThat(bus.getQueueSize(), is(0));
		bus.add(null);
		assertThat(bus.getQueueSize(), is(1));
	}

	@Test
	public void testGetQueueSize() throws InterruptedException {
		for (int i = 0; i < 5; i++) {
			bus.add(null);
		}
		assertThat(bus.getQueueSize(), is(5));
		Thread.sleep(1500);
		assertThat(bus.getQueueSize(), is(0));
	}

	@Test
	public void testProperyChanged() throws InterruptedException {
		PropertyChangeListener l = mock(PropertyChangeListener.class);

		bus.addPropertyChangeListener(l);
		Thread.sleep(5000);
		verify(l, times(5)).propertyChange((PropertyChangeEvent) anyObject());
	}
}
