package de.gzockoll.prototype.camel.observation;

public class NumberQuantity implements Quantity {
	private Unit unit;
	private Number value;

	public NumberQuantity(Unit unit, Number value) {
		super();
		this.unit = unit;
		this.value = value;
	}

	@Override
	public Unit getUnit() {
		return unit;
	}

	@Override
	public Number getValue() {
		return value;
	}
}
