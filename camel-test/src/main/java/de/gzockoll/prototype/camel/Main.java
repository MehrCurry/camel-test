package de.gzockoll.prototype.camel;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class Main {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		CamelContext context = new DefaultCamelContext();

		context.addRoutes(new RouteBuilder() {
			public void configure() {
				from("http://www.apache.org/dyn/closer.cgi/maven/binaries/apache-maven-3.0.3-bin.zip").to("file:data/outbox");
			}
		});
		context.start();
		Thread.sleep(10000);
		context.stop();
	}
}
