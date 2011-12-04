/**
 * Created 03.11.2011
 * This code is copyright (c) 2004 PAYONE Gmbh & Co. KG.
 */
package de.gzockoll.prototype.camel;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

/**
 * @author Guido Zockoll
 * 
 */
public class StockQuoteTest extends CamelTestSupport {

	/**
	 * Test method for
	 * {@link de.gzockoll.prototype.camel.PdfProcessor#process(org.apache.camel.Exchange)}
	 * .
	 * 
	 * @throws Exception
	 */

	@Test
	public void testProcess() throws Exception {
		MockEndpoint result = getMockEndpoint("mock:result");
		result.expectedMessageCount(1);

		template.sendBody("direct:input");
		result.assertIsSatisfied();
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
				errorHandler(deadLetterChannel("log:dead?level=ERROR"));
				from("direct:input")
						.to("http://finance.yahoo.com/d/quotes.csv?s=RHT+MSFT+ORCL+JAVA&f=snb3pt1d1")
						.unmarshal().csv().to("mock:result");
			}
		};
	}
}
