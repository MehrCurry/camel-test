/**
 * Created 02.11.2011
 * This code is copyright (c) 2004 PAYONE Gmbh & Co. KG.
 */
package de.gzockoll.prototype.camel;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

/**
 * @author Guido Zockoll
 * 
 */
public class LogProcessor implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        System.out.println("Hurray! We just got: " + exchange.getIn().getHeader("CamelFileName"));
    }
}
