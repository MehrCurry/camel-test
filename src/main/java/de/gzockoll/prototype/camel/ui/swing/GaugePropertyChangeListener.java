package de.gzockoll.prototype.camel.ui.swing;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import de.gzockoll.prototype.camel.Subject;
import eu.hansolo.steelseries.gauges.AbstractRadial;

public class GaugePropertyChangeListener implements PropertyChangeListener {
	private final AbstractRadial gauge;
	private final Subject subject;

	public GaugePropertyChangeListener(AbstractRadial gauge, Subject subject) {
		super();
		this.gauge = gauge;
		this.subject = subject;
		subject.addPropertyChangeListener(this);
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		gauge.setValueAnimated(1.0 * (Integer) evt.getNewValue());
	}
}
