package de.gzockoll.prototype.camel.encashment.entity;

import javax.persistence.Entity;

@Entity
public class Merchant extends NamedEntity {

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
