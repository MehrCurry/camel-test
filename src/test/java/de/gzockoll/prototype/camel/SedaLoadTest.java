package de.gzockoll.prototype.camel;

import org.apache.camel.EndpointInject;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.apache.commons.lang.time.StopWatch;
import org.junit.Test;

import de.gzockoll.observation.SimpleObservation;

public class SedaLoadTest extends CamelTestSupport {
	private static final String TEST_ENDPOINT = "vm:abc";

	@EndpointInject(uri = "mock:result")
	protected MockEndpoint resultEndpoint;

	@Produce(uri = TEST_ENDPOINT)
	protected ProducerTemplate template;

	@Test
	public void test() throws InterruptedException {
		resultEndpoint.setExpectedMessageCount(5000);
		SimpleObservation payload = new SimpleObservation("test", 3.3);
		StopWatch w = new StopWatch();
		w.start();
		for (int i = 0; i < 5000; i++)
			template.sendBody(payload);
		w.stop();
		System.out.println(w);
		System.out.println(resultEndpoint);
		resultEndpoint.assertIsSatisfied();
	}

	@Override
	protected RouteBuilder createRouteBuilder() throws Exception {
		// TODO Auto-generated method stub
		return new RouteBuilder() {

			@Override
			public void configure() throws Exception {
				from(TEST_ENDPOINT).throttle(500).to("activemq:topic:abc");
				from("activemq:topic:abc").to("mock:result");
			}
		};
	}
}
