package de.gzockoll.prototype.camel.measurement;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.processor.interceptor.DefaultTraceFormatter;
import org.apache.camel.processor.interceptor.Tracer;
import org.springframework.stereotype.Component;

@SuppressWarnings("javadoc")
@Component
final class MyRouteBuilder extends RouteBuilder {
	@Override
	public void configure() {
		Tracer tracer = new Tracer();
		tracer.setTraceOutExchanges(true);

		// we configure the default trace formatter where we can
		// specify which fields we want in the output
		DefaultTraceFormatter formatter = new DefaultTraceFormatter();
		formatter.setShowOutBody(true);
		formatter.setShowOutBodyType(true);
		formatter.setShowHeaders(true);

		// set to use our formatter
		tracer.setFormatter(formatter);
		getContext().addInterceptStrategy(tracer);

		// from("quartz://myGroup/load?cron=0+*+*+*+*+?").setBody()
		// .groovy("(1..1000).collect{new de.gzockoll.prototype.camel.Observation('Load',it)}").split(body())
		// .to("log:de.gzockoll.prototype.camel?showAll=true&multiline=true").to("seda:observations");

		from("quartz://myGroup/seconds?cron=*+*+*+*+*+?")
				.setBody()
				.groovy("new de.gzockoll.prototype.camel.observation.SimpleObservation(\"Test3\", new org.joda.time.DateTime().getSecondOfMinute()))")
				.to("log:de.gzockoll.prototype.camel?showAll=true&multiline=true")
				.to("seda:observations");

		from("quartz://myGroup/myTestTimerNameX?cron=*/10+*+*+*+*+?")
				.setBody(
						constant(InstrumentConfiguration.builder()
								.name("Test2").title("JUnit").min(0).max(100)
								.build()))
				.marshal()
				.json()
				.to("log:de.gzockoll.prototype.camel?showAll=true&multiline=true")
				.to("seda:observations");

		from("quartz://myGroup/myTimerName1?cron=0+*+*+*+*+?")
				.to("http://weather.noaa.gov/pub/data/observations/metar/stations/EDDH.TXT?disableStreamCache=true")
				.to("seda:metar");
		// from("quartz://myGroup/myTimerName2?cron=30+*+*+*+*+?").to(
		// "http://weather.noaa.gov/pub/data/observations/metar/stations/EDHK.TXT?disableStreamCache=true").to(
		// "seda:metar");
		// from("quartz://myGroup/myTimerName3?cron=31+*+*+*+*+?").to(
		// "http://weather.noaa.gov/pub/data/observations/metar/stations/LOWI.TXT?disableStreamCache=true").to(
		// "seda:metar");

		from("seda:metar")
				.to("log:de.gzockoll.prototype.camel?showAll=true&multiline=true")
				.process(new MetarProcessor())
				.to("log:de.gzockoll.prototype.camel?showAll=true&multiline=true")
				.split(body()).to("seda:observations");

		from("seda:observations").convertBodyTo(String.class).to(
				"activemq:topic:observations?timeToLive=15000");

		from("activemq:topic:observations")
				.aggregate(constant(true), new ArrayListAggregationStrategy())
				.completionInterval(5000L)
				.eagerCheckCompletion()
				.process(new CountProcessor("Messages", 5))
				.to("log:de.gzockoll.prototype.camel?showAll=true&multiline=true")
				.to("seda:observations");

		from("activemq:topic:observations").throttle(3).timePeriodMillis(1000)
				.asyncDelayed()
				// .to("log:de.gzockoll.prototype.camel?showAll=true&multiline=true")
				.to("activemq:topic:observationsWeb");

		// from("file:data/inbox/?noop=true&delay=10000").to("seda:input");
		//
		// from("ftp://ftpuser@zockoll.dyndns.org/in?password=tux88.").to(
		// "seda:input");
		//
		// from("seda:input").to("log:de.gzockoll.prototype.camel").multicast()
		// .to("seda:process").to("seda:audit").end();
		//
		// from("seda:process")
		// .errorHandler(deadLetterChannel("seda:error"))
		// //
		// .maximumRedeliveries(5).retryAttemptedLogLevel(LoggingLevel.INFO).redeliveryDelay(250).backOffMultiplier(2))
		// .unmarshal()
		// .bindy(BindyType.Csv, "de.gzockoll.prototype.camel.bindy")
		// .split(body(List.class))
		// .to("log:de.gzockoll.prototype.camel?showAll=true&multiline=true")
		// .convertBodyTo(Ueberweisung.class)
		// //
		// .to("log:de.gzockoll.prototype.camel?showAll=true&multiline=true").process(new
		// // AccountingProcessor())
		// .to("log:de.gzockoll.prototype.camel?showAll=true&multiline=true")
		// .to("mock:data");
		//
		// from("seda:audit?concurrentConsumers=5").to("file:data/audit/");

		from("seda:error")
				.marshal()
				.xstream("UTF-8")
				.setHeader(
						Exchange.FILE_NAME,
						simple("${file:name.noext}-${header:breadcrumbId}-${date:now:yyyyMMddHHmmssSSS}.xml"))
				.to("log:de.gzockoll.prototype.camel?showAll=true&multiline=true")
				.to("file:data/error");
	}
}
