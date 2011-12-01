package de.gzockoll.prototype.camel.encashment;

import javax.persistence.Entity;

@Entity
public class Merchant extends NamedEntity {

	public Merchant(String name) {
		super(name);
	}
}
