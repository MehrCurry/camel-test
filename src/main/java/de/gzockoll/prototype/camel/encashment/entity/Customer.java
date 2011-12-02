package de.gzockoll.prototype.camel.encashment.entity;

import javax.persistence.Entity;

import org.apache.camel.dataformat.bindy.annotation.CsvRecord;

@Entity
@CsvRecord(separator = ",", generateHeaderColumns = true)
public class Customer extends NamedEntity {

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
