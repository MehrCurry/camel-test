package de.gzockoll.prototype.camel;

import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.BindyType;

@SuppressWarnings("javadoc")
final class MyRouteBuilder extends RouteBuilder {
    @Override
    public void configure() {
        from("file:data/inbox/?noop=true&delay=10000").to("seda:input");

        from("ftp://ftpuser@zockoll.dyndns.org/in?password=tux88.").to("seda:input");

        from("seda:input").to("log:de.gzockoll.prototype.camel").multicast().to("seda:process").to("seda:audit").end();

        from("seda:process")
                .errorHandler(deadLetterChannel("seda:error"))
                // .maximumRedeliveries(5).retryAttemptedLogLevel(LoggingLevel.INFO).redeliveryDelay(250).backOffMultiplier(2))
                .unmarshal().bindy(BindyType.Csv, "de.gzockoll.prototype.camel.bindy").split(body(List.class))
                .to("log:de.gzockoll.prototype.camel?showAll=true&multiline=true").convertBodyTo(Ueberweisung.class)
                .to("log:de.gzockoll.prototype.camel?showAll=true&multiline=true").process(new AccountingProcessor())
                .to("log:de.gzockoll.prototype.camel?showAll=true&multiline=true").to("mock:data");

        from("seda:audit?concurrentConsumers=5").to("file:data/audit/");

        from("seda:error")
                .marshal()
                .xstream("UTF-8")
                .setHeader(Exchange.FILE_NAME,
                        simple("${file:name.noext}-${header:breadcrumbId}-${date:now:yyyyMMddHHmmssSSS}.xml"))
                .to("log:de.gzockoll.prototype.camel?showAll=true&multiline=true").to("file:data/error");
    }
}
