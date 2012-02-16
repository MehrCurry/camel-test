/**
 * Created 16.02.2012
 * This code is copyright (c) 2004 PAYONE Gmbh & Co. KG.
 */
package de.gzockoll.prototype.camel;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Guido Zockoll
 * 
 */
public class DebugProcessor implements Processor {
    private static final Logger logger = LoggerFactory.getLogger(DebugProcessor.class);
    private String name;

    /**
     * Create a new DebugProcessor.
     * 
     * @param name
     */
    public DebugProcessor(String name) {
        super();
        this.name = name;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.apache.camel.Processor#process(org.apache.camel.Exchange)
     */
    @Override
    public void process(Exchange exchange) throws Exception {
        logger.debug(exchange.toString());
    }
}
