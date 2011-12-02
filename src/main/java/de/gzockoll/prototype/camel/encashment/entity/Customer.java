package de.gzockoll.prototype.camel.encashment.entity;

import javax.persistence.Entity;

@Entity
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
