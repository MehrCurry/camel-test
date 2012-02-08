package de.gzockoll.prototype.camel.observation;

public class Measurement extends Observation {
	private PhanomenonType type;
	private Quantity quantity;

	public Measurement(Subject subject, PhanomenonType type, Quantity quantity) {
		super(subject);
		this.type = type;
		this.quantity = quantity;
	}

	@Override
	public Object getValue() {
		return quantity;
	}
}
