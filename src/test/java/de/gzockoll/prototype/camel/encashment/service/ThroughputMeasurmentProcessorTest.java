package de.gzockoll.prototype.camel.encashment.service;

import static org.hamcrest.core.Is.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ThroughputMeasurmentProcessorTest {
	private ThroughputMeasurmentProcessor bus;

	@Before
	public void setUp() {
		bus = new ThroughputMeasurmentProcessor(1000);
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
		Thread.sleep(500);
		for (int i = 0; i < 5; i++) {
			bus.add(null);
		}
		assertThat(bus.getQueueSize(), is(10));
		Thread.sleep(600);
		assertThat(bus.getQueueSize(), is(5));
		Thread.sleep(500);
		assertThat(bus.getQueueSize(), is(0));
	}
}
