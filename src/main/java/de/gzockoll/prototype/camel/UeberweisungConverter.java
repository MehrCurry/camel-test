package de.gzockoll.prototype.camel;

import java.util.Map;

import org.apache.camel.Converter;
import org.apache.commons.lang.Validate;

import de.gzockoll.prototype.camel.bindy.UeberweisungTO;

@Converter
public class UeberweisungConverter {

    @Converter
    public Ueberweisung convert(Map<String, UeberweisungTO> aMap) {
        // Validate.isTrue(to.isValid());
        Validate.isTrue(aMap.size() == 1);
        String key = UeberweisungTO.class.getName();
        Validate.isTrue(aMap.keySet().contains(key));

        UeberweisungTO to = aMap.get(key);
        return new Ueberweisung.Builder().name(to.getName()).betrag(to.getMoney()).zweck(to.getText()).build();
    }
}
