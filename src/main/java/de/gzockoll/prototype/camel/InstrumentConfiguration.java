/**
 * Created 08.02.2012
 * This code is copyright (c) 2004 PAYONE Gmbh & Co. KG.
 */
package de.gzockoll.prototype.camel;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.math.Range;

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
    private Set<ColoredRange> areas = new HashSet<ColoredRange>();
    private Set<ColoredRange> sections = new HashSet<ColoredRange>();

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

        public Builder area(ColoredRange... ranges) {
            conf.areas.addAll(Arrays.asList(ranges));
            return this;
        }

        public Builder section(ColoredRange... ranges) {
            conf.sections.addAll(Arrays.asList(ranges));
            return this;
        }

        public InstrumentConfiguration build() {
            return conf;
        }
    }
}
