package de.gzockoll.prototype.camel;

import java.util.List;

import org.apache.camel.builder.RouteBuilder;

final class MyRouteBuilder extends RouteBuilder {
    @Override
    public void configure() {
        /*
         * XStream xstream = new XStream(); xstream.processAnnotations(Candidate.class);
         * 
         * XStreamDataFormat xStreamDataFormat = new XStreamDataFormat(); xStreamDataFormat.setXStream(xstream);
         * 
         * //Intercept the exceptions onException(Exception.class) .handled(true) //Convert the object to XML
         * .marshal(xStreamDataFormat) //Send the result to a JMS queue .to("jms:queue.candidate.rejected");
         */

        from("file:data/inbox/?fileName=data.csv&noop=true").unmarshal().csv()
                .to("log:de.gzockoll.prototype.camel?showAll=true&multiline=true").split(body(List.class))
                .to("log:de.gzockoll.prototype.camel?showAll=true&multiline=true").convertBodyTo(Ueberweisung.class)
                .to("log:de.gzockoll.prototype.camel?showAll=true&multiline=true").to("mock:data");
    }
}