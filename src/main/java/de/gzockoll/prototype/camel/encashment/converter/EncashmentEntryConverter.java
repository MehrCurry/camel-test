/**
 * Created 02.12.2011
 * This code is copyright (c) 2004 PAYONE Gmbh & Co. KG.
 */
package de.gzockoll.prototype.camel.encashment.converter;

import org.apache.camel.Converter;
import org.apache.camel.Exchange;

import de.gzockoll.prototype.camel.encashment.entity.EncashmentEntry;

/**
 * @author Guido Zockoll
 * 
 */
@Converter
public class EncashmentEntryConverter {

    @Converter
    public String asString(EncashmentEntry entry, Exchange exchange) {
        return entry.toString();
    }
}
