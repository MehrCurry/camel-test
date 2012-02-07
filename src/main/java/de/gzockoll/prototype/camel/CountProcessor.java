/**
 * Created 06.02.2012
 * This code is copyright (c) 2004 PAYONE Gmbh & Co. KG.
 */
package de.gzockoll.prototype.camel;

import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Guido Zockoll
 * 
 */
public class CountProcessor implements Processor {
    private static final Logger logger = LoggerFactory.getLogger(CountProcessor.class);

    /*
     * (non-Javadoc)
     * 
     * @see org.apache.camel.Processor#process(org.apache.camel.Exchange)
     */
    @Override
    public void process(Exchange exchange) throws Exception {
        List result = (List) exchange.getIn().getBody();
        logger.debug(result == null ? "List <null>" : "Listsize " + result.size());

        exchange.getIn().setBody(new Observation("MessagesPerMinute", result.size() / 10.0));
    }
}
