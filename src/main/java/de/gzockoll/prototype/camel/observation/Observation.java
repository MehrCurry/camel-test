package de.gzockoll.prototype.camel.observation;

import org.joda.time.DateTime;

public abstract class Observation {
    protected final DateTime timeTaken = new DateTime();
    protected Subject subject;

    public Observation(Subject subject) {
        super();
        this.subject = subject;
    }

    @Override
    public String toString() {
        return toJSON();
        // return ReflectionToStringBuilder.toString(this);
    }

    public abstract String toJSON();

    public abstract Object getValue();
}
