/**
 * Created 15.11.2011
 * This code is copyright (c) 2004 PAYONE Gmbh & Co. KG.
 */
package de.gzockoll.prototype.camel.bindy;

import java.util.Currency;

import org.apache.camel.dataformat.bindy.annotation.CsvRecord;
import org.apache.camel.dataformat.bindy.annotation.DataField;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.joda.money.Money;

/**
 * @author Guido Zockoll
 * 
 */
@CsvRecord(separator = ",", skipFirstLine = true)
public class UeberweisungTO {

    @DataField(pos = 1)
    private String name;
    @DataField(pos = 2)
    private String value;
    @DataField(pos = 3)
    private String currency;
    @DataField(pos = 4)
    private String text;

    /**
     * @return the currency
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * @return
     */
    public Money getMoney() {
        return Money.parse(currency + " " + value);
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the text
     */
    public String getText() {
        return text;
    }

    /**
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * @return
     */
    public boolean isValid() {
        return StringUtils.isNotEmpty(name) && StringUtils.isNotEmpty(text) && StringUtils.isNotEmpty(value)
                && Currency.getInstance(currency) != null;
    }

    /**
     * @param currency
     *            the currency to set
     */
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @param text
     *            the text to set
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * @param value
     *            the value to set
     */
    public void setValue(String value) {
        this.value = value;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return new ReflectionToStringBuilder(this).toString();
    }
}
