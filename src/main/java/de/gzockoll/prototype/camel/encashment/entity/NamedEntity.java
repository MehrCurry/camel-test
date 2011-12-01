package de.gzockoll.prototype.camel.encashment.entity;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class NamedEntity {
	protected String name;

	public NamedEntity(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
