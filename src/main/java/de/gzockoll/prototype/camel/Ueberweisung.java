package de.gzockoll.prototype.camel;

import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

public class Ueberweisung {
    public static class Builder {
        private final Ueberweisung ueberweisung = new Ueberweisung();

        /**
         * @param value
         * @return
         */
        public Builder betrag(Money value) {
            Validate.notNull(value);
            ueberweisung.setBetrag(value);
            return this;
        }

        public Builder betrag(String betrag, String currencyCode) {
            Validate.notEmpty(betrag);
            Validate.notEmpty(currencyCode);
            CurrencyUnit currency = CurrencyUnit.getInstance(currencyCode);
            ueberweisung.setBetrag(Money.ofMinor(currency,
                    new BigDecimal(betrag).multiply(TENS[currency.getDecimalPlaces()]).longValue()));
            return this;
        }

        public Ueberweisung build() {
            Validate.isTrue(ueberweisung.isValid());
            return ueberweisung;
        }

        public Builder name(String name) {
            Validate.notEmpty(name);
            ueberweisung.setName(name);
            return this;
        }

        public Builder zweck(String aString) {
            ueberweisung.setText(aString);
            return this;
        }

    }

    public static BigDecimal[] TENS = { new BigDecimal(1), new BigDecimal(10), new BigDecimal(100),
            new BigDecimal(1000) };

    private String name;
    private Money value;
    private String text;

    public Ueberweisung(String name, Money value, String text) {
        super();
        this.name = name;
        this.value = value;
        this.text = text;
    }

    private Ueberweisung() {
    }

    public String getName() {
        return name;
    }

    public String getText() {
        return text;
    }

    public Money getValue() {
        return value;
    }

    public boolean isValid() {
        return (!StringUtils.isEmpty(name) && !StringUtils.isEmpty(text) && value != null);
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

    private void setBetrag(Money value) {
        this.value = value;
    }

    private void setName(String name) {
        this.name = name;
    }

    private void setText(String aString) {
        this.text = aString;

    }
}
