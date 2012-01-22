package de.gzockoll.prototype.camel.encashment;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;

import de.gzockoll.prototype.camel.encashment.entity.EncashmentType;
import de.gzockoll.prototype.camel.encashment.service.EncashmentService;

@SuppressWarnings("javadoc")
public final class MyRouteBuilder extends RouteBuilder {
	private static final String TRANSPORT = "seda";

	// private static final String TRANSPORT = "activemq:quene";

	@Override
	public void configure() {
		errorHandler(deadLetterChannel("seda:error").maximumRedeliveries(3)
				.retryAttemptedLogLevel(LoggingLevel.WARN).useOriginalMessage()
				.redeliveryDelay(5000).backOffMultiplier(2));

		// from("quartz://myGroup/restCall?cron=0+*/5+*+*+*+?").setHeader(
		// Exchange.HTTP_METHOD, constant("GET")).to(
		// "http://timecard.gzockoll.de/entry/show/612");

		from("quartz://myGroup/myTimerName?cron=*/30+*+*+*+*+?").bean(
				EncashmentService.class, "startProcessing()");

		from("direct:input").to(TRANSPORT + ":inkasso1");

		from(TRANSPORT + ":inkasso1")
				.wireTap("activemq:topic:controlbus")
				.to("log:de.gzockoll.prototype.camel?showAll=true&multiline=true")
				.multicast()
				.to("seda:filemanager")
				.choice()
				.when(property("TYPE").isEqualTo(EncashmentType.ORDER.name()))
				.to("seda:inkasso1_order")
				.when(property("TYPE").isEqualTo(EncashmentType.CREDIT.name()))
				.to("seda:inkasso1_credit")
				.when(property("TYPE").isEqualTo(EncashmentType.PAYMENT.name()))
				.to("seda:inkasso1_payment").otherwise().to("seda:error").end();

		from("seda:inkasso1_order").marshal().json()
				.wireTap("activemq:topic:controlbus")
				.to("ftp://ftpuser@zockoll.dyndns.org/out?password=tux88.")
				.to("seda:success");

		from("seda:inkasso1_credit").marshal().json()
				.wireTap("activemq:topic:controlbus")
				.setHeader("subject", constant("Credit received"))
				.setHeader("to", constant("gzockoll@gmail.com"))
				.to("seda:gmail-wrong");

		from("seda:inkasso1_payment").marshal().json()
				.wireTap("activemq:topic:controlbus")
				.setHeader("subject", constant("PAYMENT made"))
				.setHeader("to", constant("gzockoll@gmail.com"))
				.to("seda:gmail");

		from("seda:gmail").wireTap("activemq:topic:controlbus")
				.to("smtps://gztest999@smtp.gmail.com?password=fifi9999")
				.to("seda:success");

		from("seda:gmail-wrong").wireTap("activemq:topic:controlbus")
				.to("smtps://wrong@smtp.gmail.com?password=wrong")
				.to("seda:success");

		from("seda:filemanager")
				.wireTap("activemq:topic:controlbus")
				.marshal()
				.xstream("UTF-8")
				.to("log:de.gzockoll.prototype.camel?showAll=true&multiline=true")
				.to("file:data/outbox");

		from("seda:success").bean(EncashmentService.class, "onSuccess()");

		from("seda:error")
				.wireTap("activemq:topic:controlbus")
				.marshal()
				.json()
				.to("log:de.gzockoll.prototype.camel?showAll=true&multiline=true")
				.bean(EncashmentService.class, "onError()");

		from("activemq:topic:controlbus").processRef("tpp1").throttle(3).processRef("tpp2");

	}
}
