package de.gzockoll.prototype.camel;

import java.util.List;

import org.apache.camel.Converter;
import org.apache.commons.lang.Validate;
import org.joda.money.Money;

@Converter
public class UeberweisungConverter {

    public class DataParser {
        private final List<String> data;

        /**
         * Create a new UeberweisungConverter.DataParser.
         */
        public DataParser(List<String> data) {
            this.data = data;
        }

        public String getName() {
            return data.get(0);
        }

        public String getText() {
            return data.get(3);
        }

        public Money getValue() {
            return Money.parse(data.get(2) + " " + data.get(1));
        }
    }

    @Converter
    public Ueberweisung convert(List data) {
        Validate.isTrue(data.size() == 4, "Invalid data passed:" + data);
        DataParser parser = new DataParser(data);

        return new Ueberweisung.Builder().name(parser.getName()).betrag(parser.getValue()).zweck(parser.getText())
                .build();
    }
}
