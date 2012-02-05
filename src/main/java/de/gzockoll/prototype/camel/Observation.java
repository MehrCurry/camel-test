package de.gzockoll.prototype.camel;

public class Observation {
	private String name;
	private Number value;

	public Observation(String name, Number value) {
		super();
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public Number getValue() {
		return value;
	}

	@Override
	public String toString() {
		return toJSON();
		// return ReflectionToStringBuilder.toString(this);
	}

	public String toJSON() {
		return "{ \"key\" : \"" + name + "\", \"value\" : " + value + "}";
	}
}
