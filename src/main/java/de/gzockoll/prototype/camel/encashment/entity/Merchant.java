package de.gzockoll.prototype.camel.encashment.entity;

import javax.persistence.Entity;

import org.apache.camel.dataformat.bindy.annotation.CsvRecord;

@Entity
@CsvRecord(separator = ",", generateHeaderColumns = true)
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
