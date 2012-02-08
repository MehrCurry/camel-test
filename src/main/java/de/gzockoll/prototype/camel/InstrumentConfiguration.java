/**
 * Created 08.02.2012
 * This code is copyright (c) 2004 PAYONE Gmbh & Co. KG.
 */
package de.gzockoll.prototype.camel;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

/**
 * @author Guido Zockoll
 * 
 */
public class InstrumentConfiguration {
    private String name = null;
    private String title = null;
    private String unit = null;
    private Number min = Double.NaN;
    private Number max = Double.NaN;
    private Number threshold = Double.NaN;

    public static Builder builder() {
        return new Builder();
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

    public static class Builder {
        private InstrumentConfiguration conf = new InstrumentConfiguration();

        public Builder name(String name) {
            conf.name = name;
            return this;
        }

        public Builder title(String title) {
            conf.title = title;
            return this;
        }

        public Builder unit(String unit) {
            conf.unit = unit;
            return this;
        }

        public Builder min(Number n) {
            conf.min = n;
            return this;
        }

        public Builder max(Number n) {
            conf.max = n;
            return this;
        }

        public Builder threshold(Number n) {
            conf.threshold = n;
            return this;
        }

        public InstrumentConfiguration build() {
            return conf;
        }
    }
}
