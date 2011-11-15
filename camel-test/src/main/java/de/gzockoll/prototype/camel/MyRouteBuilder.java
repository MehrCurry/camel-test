package de.gzockoll.prototype.camel;

import java.util.List;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.BindyType;

final class MyRouteBuilder extends RouteBuilder {
    @Override
    public void configure() {
        errorHandler(deadLetterChannel("log:dead?level=ERROR"));
        from("file:data/inbox/?fileName=data.csv&noop=true")
                .to("log:de.gzockoll.prototype.camel?showAll=true&multiline=true").unmarshal()
                .bindy(BindyType.Csv, "de.gzockoll.prototype.camel.bindy")
                .to("log:de.gzockoll.prototype.camel?showAll=true&multiline=true").split(body(List.class))
                .to("log:de.gzockoll.prototype.camel?showAll=true&multiline=true").convertBodyTo(Ueberweisung.class)
                .to("log:de.gzockoll.prototype.camel?showAll=true&multiline=true").to("mock:data");
    }
}