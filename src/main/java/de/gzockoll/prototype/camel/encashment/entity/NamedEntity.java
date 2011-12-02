package de.gzockoll.prototype.camel.encashment.entity;

import javax.persistence.MappedSuperclass;

import org.apache.camel.dataformat.bindy.annotation.DataField;

@MappedSuperclass
public abstract class NamedEntity extends AbstractEntity {
    @DataField(pos = 1)
    protected String name;

    public NamedEntity(String name) {
        super();
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
