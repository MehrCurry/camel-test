package de.gzockoll.prototype.camel.observation;

public class CategoryObservation extends Observation {
	private Phanomenon phanomenon;

	public CategoryObservation(Subject subject, Phanomenon phanomenon) {
		super(subject);
		this.phanomenon = phanomenon;
	}

	@Override
	public Object getValue() {
		return phanomenon;
	}
}
