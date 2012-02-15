package de.gzockoll.prototype.camel.encashment.service;

import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.joda.time.DateTime;
import org.joda.time.ReadableInstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ThroughputMeasurmentProcessor implements Processor {
	private static final Logger logger = LoggerFactory
			.getLogger(ThroughputMeasurmentProcessor.class);

	private static final long serialVersionUID = 1L;
	Queue<ControlBusEntry> data = new LinkedList<ControlBusEntry>();

	private final long delay;

	public ThroughputMeasurmentProcessor(long delay) {
		this.delay = delay;
	}

	public void add(Exchange ex) {
		removeExpiredEntries();
		data.add(new ControlBusEntry(ex));
	}

	public int getQueueSize() {
		removeExpiredEntries();
		return data.size();
	}

	private void removeExpiredEntries() {
		try {
			while (data.size() > 0) {
				ControlBusEntry e = data.element();
				if (e.isBefore(new DateTime().minusMillis((int) delay))) {
					data.remove();
					logger.debug("Removed: " + e);
				} else
					break;

			}
		} catch (NoSuchElementException e) {
		}
	}

	private static class ControlBusEntry {
		private final DateTime created = new DateTime();
		private final Exchange exchange;

		public ControlBusEntry(Exchange exchange) {
			super();
			this.exchange = exchange;
		}

		public boolean isBefore(ReadableInstant ri) {
			return created.isBefore(ri);
		}

		public Exchange getExchange() {
			return exchange;
		}

		@Override
		public String toString() {
			return new ReflectionToStringBuilder(this).toString();
		}
	}

	@Override
	public void process(Exchange ex) throws Exception {
		add(ex);
	}
}
