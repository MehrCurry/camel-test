package de.gzockoll.prototype.camel.encashment.entity;

import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.apache.camel.dataformat.bindy.annotation.CsvRecord;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "name" }))
@NamedQuery(name = Customer.FIND_BY_NAME, query = "Select c from Customer c where c.name = :name")
@CsvRecord(separator = ",", generateHeaderColumns = true)
public class Customer extends NamedEntity {
	public static final String FIND_BY_NAME = "Customer.findByName";

	public Customer(String name) {
		super(name);
	}

	/**
	 * Create a new Customer.
	 */
	public Customer() {
		super("<NoName>");
	}
}
