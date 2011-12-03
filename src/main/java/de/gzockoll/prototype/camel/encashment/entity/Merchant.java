package de.gzockoll.prototype.camel.encashment.entity;

import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.apache.camel.dataformat.bindy.annotation.CsvRecord;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "name" }))
@NamedQuery(name = Merchant.FIND_BY_NAME, query = "Select m from Merchant m where m.name = :name")
@CsvRecord(separator = ",", generateHeaderColumns = true)
public class Merchant extends NamedEntity {
	public static final String FIND_BY_NAME = "Merchant.findByName";

	public Merchant(String name) {
		super(name);
	}

	/**
	 * Create a new Merchant.
	 */
	public Merchant() {
		super("<NoName>");
	}
}
