package de.gzockoll.prototype.camel.encashment.entity;

import javax.persistence.Entity;

@Entity
public class Customer extends NamedEntity {

	public Customer(String name) {
		super(name);
	}
}
