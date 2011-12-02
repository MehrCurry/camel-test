package de.gzockoll.prototype.camel.encashment.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

@MappedSuperclass
public abstract class AbstractEntity {

    @Id
    @GeneratedValue
    protected Long id;

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        return new ReflectionToStringBuilder(this).toString();
    }

}
