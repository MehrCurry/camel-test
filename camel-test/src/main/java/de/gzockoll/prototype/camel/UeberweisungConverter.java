package de.gzockoll.prototype.camel;

import java.util.List;

import org.apache.camel.Converter;

@Converter
public class UeberweisungConverter {

	@Converter
	public Ueberweisung convert(List data) {
		return new Ueberweisung.Builder().name((String) data.get(0))
				.betrag((String)data.get(1),(String)data.get(2)).zweck((String) data.get(3))
				.build();
	}
}
