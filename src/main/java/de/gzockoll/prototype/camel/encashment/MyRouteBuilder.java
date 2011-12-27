package de.gzockoll.prototype.camel.encashment;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;

import de.gzockoll.prototype.camel.encashment.entity.EncashmentType;
import de.gzockoll.prototype.camel.encashment.service.EncashmentService;

@SuppressWarnings("javadoc")
public final class MyRouteBuilder extends RouteBuilder {

    @Override
    public void configure() {
        errorHandler(deadLetterChannel("seda:error").maximumRedeliveries(3).retryAttemptedLogLevel(LoggingLevel.WARN)
                .useOriginalMessage().redeliveryDelay(3000).backOffMultiplier(2));

        from("quartz://myGroup/myTimerName?cron=*/30+*+*+*+*+?").bean(EncashmentService.class, "startProcessing()");

        from("direct:input").to("seda" + ":inkasso1");

        from("seda" + ":inkasso1").multicast().to("activemq:quene:filemanager").choice()
                .when(property("TYPE").isEqualTo(EncashmentType.ORDER.name())).to("seda:inkasso1_order")
                .when(property("TYPE").isEqualTo(EncashmentType.CREDIT.name())).to("seda:inkasso1_credit")
                .when(property("TYPE").isEqualTo(EncashmentType.PAYMENT.name())).to("seda:inkasso1_payment")
                .otherwise().to("seda:error").end();

        from("seda:inkasso1_order").marshal().json().setHeader("username", constant("ftpuser"))
                .setHeader("password", constant("tux88.")).to("seda:ftp");

        from("seda:inkasso1_credit").marshal().json().setHeader("username", constant("anonymous"))
                .setHeader("password", constant("ftp@ftp")).to("seda:ftp");

        from("seda:inkasso1_payment").marshal().json().setHeader("subject", constant("PAYMENT made"))
                .setHeader("to", constant("gzockoll@gmail.com")).to("seda:gmail");

        from("seda:gmail").to("smtps://gztest999@smtp.gmail.com?password=fifi9999").to("seda:success");

        from("seda:gmail-wrong").to("smtps://wrong@smtp.gmail.com?password=wrong").to("seda:success");

        from("seda:ftp").to("log:de.gzockoll.prototype.camel?showAll=true&multiline=true")
                .to("ftps://zockoll.dyndns.org/out?passiveMode=true").to("seda:success");

        from("activemq:quene:filemanager").marshal().xstream("UTF-8")
                .to("log:de.gzockoll.prototype.camel?showAll=true&multiline=true")
                .to("jcr://test:test@repository/filemanager");

        from("seda:success").bean(EncashmentService.class, "onSuccess()");

        from("seda:error")
                .to("log:de.gzockoll.prototype.camel?showAll=true&multiline=true")
                .marshal()
                .json()
                .setHeader(Exchange.FILE_NAME,
                        simple("${file:name.noext}-${header:breadcrumbId}-${date:now:yyyyMMddHHmmssSSS}.xml"))
                .to("log:de.gzockoll.prototype.camel?showAll=true&multiline=true").to("file:data/error")
                .to("activemq:topic:error").bean(EncashmentService.class, "onError()");
    }
}
