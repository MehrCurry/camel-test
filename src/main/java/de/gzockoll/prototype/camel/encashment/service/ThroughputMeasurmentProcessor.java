package de.gzockoll.prototype.camel.encashment.service;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.joda.time.DateTime;
import org.joda.time.ReadableInstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.gzockoll.prototype.camel.Subject;

public class ThroughputMeasurmentProcessor extends TimerTask implements
		Processor, Subject {
	private static final Logger logger = LoggerFactory
			.getLogger(ThroughputMeasurmentProcessor.class);
	private final PropertyChangeSupport changes = new PropertyChangeSupport(
			this);

	private final Timer t = new Timer();

	private int queueLength = 0;

	private static final long serialVersionUID = 1L;
	Queue<ControlBusEntry> data = new LinkedList<ControlBusEntry>();

	private final long delay;

	public ThroughputMeasurmentProcessor(long delay) {
		this.delay = delay;
		t.scheduleAtFixedRate(this, 0, delay);
	}

	public void add(Exchange ex) {
		data.add(new ControlBusEntry(ex));
	}

	public int getQueueSize() {
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

		return data.size();
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
	public void run() {
		System.out.println("Tick ...");
		changes.firePropertyChange("queueLength", queueLength,
				queueLength = getQueueSize());
		System.out.println("QueueLengt: " + queueLength);
	}

	@Override
	public void process(Exchange ex) throws Exception {
		add(ex);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.gzockoll.prototype.camel.encashment.service.Subject#
	 * addPropertyChangeListener(java.beans.PropertyChangeListener)
	 */
	@Override
	public void addPropertyChangeListener(PropertyChangeListener l) {
		changes.addPropertyChangeListener(l);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.gzockoll.prototype.camel.encashment.service.Subject#
	 * removePropertyChangeListener(java.beans.PropertyChangeListener)
	 */
	@Override
	public void removePropertyChangeListener(PropertyChangeListener l) {
		changes.removePropertyChangeListener(l);
	}

	public void stop() {
		t.cancel();
	}
}
