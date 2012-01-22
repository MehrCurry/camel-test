package de.gzockoll.prototype.camel;

import java.beans.PropertyChangeListener;

public interface Subject {

	public abstract void addPropertyChangeListener(PropertyChangeListener l);

	public abstract void removePropertyChangeListener(PropertyChangeListener l);

}