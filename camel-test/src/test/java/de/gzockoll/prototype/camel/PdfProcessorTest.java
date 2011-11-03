/**
 * Created 03.11.2011
 * This code is copyright (c) 2004 PAYONE Gmbh & Co. KG.
 */
package de.gzockoll.prototype.camel;

import java.io.File;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

/**
 * @author Guido Zockoll
 * 
 */
public class PdfProcessorTest extends CamelTestSupport {

    /**
     * Test method for {@link de.gzockoll.prototype.camel.PdfProcessor#process(org.apache.camel.Exchange)}.
     * 
     * @throws Exception
     */

    @Test
    public void testProcess() throws Exception {
        MockEndpoint pdf = getMockEndpoint("mock:pdf");
        pdf.expectedMessageCount(1);
        pdf.expectedPropertyReceived("Category", "Invoice");

        template.sendBody("file:src/test/resources?noop=true", new File(
                "src/test/resources/20110421-Hotel Aviva0001.pdf"));
        pdf.assertIsSatisfied();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.apache.camel.test.junit4.CamelTestSupport#createRouteBuilder()
     */
    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new RouteBuilder() {

            @Override
            public void configure() throws Exception {
                from("file:src/test/resources?noop=true").process(new PdfProcessor()).to("mock:pdf");
            }
        };
    }
}
