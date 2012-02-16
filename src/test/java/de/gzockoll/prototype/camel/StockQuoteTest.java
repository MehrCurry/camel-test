/**
 * Created 03.11.2011
 * This code is copyright (c) 2004 PAYONE Gmbh & Co. KG.
 */
package de.gzockoll.prototype.camel;

import org.apache.camel.EndpointInject;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

/**
 * @author Guido Zockoll
 * 
 */
public class StockQuoteTest extends CamelTestSupport {

	@EndpointInject(uri = "mock:result")
	protected MockEndpoint resultEndpoint;

	@Produce(uri = "direct:start")
	protected ProducerTemplate template;

	/**
	 * Test method for
	 * {@link de.gzockoll.prototype.camel.PdfProcessor#process(org.apache.camel.Exchange)}
	 * .
	 * 
	 * @throws Exception
	 */

	@Test
	public void testProcess() throws Exception {
		resultEndpoint.expectedMessageCount(1);

		template.sendBody("JUnit");
		resultEndpoint.assertIsSatisfied();
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
				from("direct:start")
						.to("http://finance.yahoo.com/d/quotes.csv?s=RHT+MSFT+ORCL+JAVA&f=snb3pt1d1")
						.unmarshal().csv().log("junit").to("mock:result");
			}
		};
	}
}
