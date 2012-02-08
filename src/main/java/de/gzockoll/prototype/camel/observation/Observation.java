package de.gzockoll.prototype.camel.observation;

import org.joda.time.DateTime;

public abstract class Observation {
	private final DateTime timeTaken = new DateTime();
	private Subject subject;

	public Observation(Subject subject) {
		super();
		this.subject = subject;
	}

	@Override
	public String toString() {
		return toJSON();
		// return ReflectionToStringBuilder.toString(this);
	}

	public String toJSON() {
		return "{ \"key\" : \"" + subject.getName() + "\", \"value\" : "
				+ getValue() + "}";
	}

	public abstract Object getValue();
}
