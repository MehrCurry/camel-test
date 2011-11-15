package de.gzockoll.prototype.camel;

import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;

public class Main {

    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        CamelContext context = new DefaultCamelContext();

        // AnnotationTypeConverterLoader loader=new AnnotationTypeConverterLoader(resolver)
        context.addRoutes(new MyRouteBuilder());

        context.setTracing(true);
        context.start();
        Thread.sleep(2000);
        context.stop();
    }
}
