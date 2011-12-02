package de.gzockoll.prototype.camel.encashment;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import org.apache.camel.CamelContext;
import org.apache.camel.Endpoint;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Producer;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.processor.interceptor.DefaultTraceFormatter;
import org.apache.camel.processor.interceptor.Tracer;
import org.apache.commons.lang.Validate;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import de.gzockoll.prototype.camel.encashment.service.EncashmentService;

@SuppressWarnings("javadoc")
public class Main {

    private class WindowEventHandler extends WindowAdapter {
        private final CamelContext context;

        public WindowEventHandler(CamelContext context) {
            this.context = context;
        }

        @Override
        public void windowClosing(WindowEvent e) {
            super.windowClosing(e);
            onExit();
        }
    }

    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        new Main().run();
    }

    private CamelContext context;
    private ApplicationContext springContext;

    public void run() throws Exception {
        springContext = new ClassPathXmlApplicationContext(new String[] { "/data-beans.xml", "/control-beans.xml",
                "/camel-beans.xml" });

        startEncashment();

        // createCamelContext();
        showFrame();

        // sendTestMessage();

    }

    /**
     * @throws Exception
     */
    private void createCamelContext() throws Exception {
        context = new DefaultCamelContext();
        context.getManagementStrategy().getManagementAgent().setCreateConnector(true);

        context.addRoutes(new MyRouteBuilder());

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

        context.addInterceptStrategy(tracer);
        context.setTracing(true);
        context.start();
    }

    private void startEncashment() {
        EncashmentService service = springContext.getBean(EncashmentService.class);
        Validate.notNull(service);
        service.populateDatabase();

    }

    private void sendTestMessage() throws Exception {
        // create an exchange with a normal body and attachment to be produced
        // as email
        Endpoint endpoint = context.getEndpoint("direct:input");

        // create the exchange with the mail message that is multipart with a
        // file and a Hello World text/plain message.
        Exchange exchange = endpoint.createExchange();
        exchange.setProperty("TYPE", EncashmentType.CREDIT.name());
        Message in = exchange.getIn();
        in.setBody("Hello World");
        in.addAttachment("data.csv", new DataHandler(new FileDataSource("data/inbox/data.csv")));

        // create a producer that can produce the exchange (= send the mail)
        Producer producer = endpoint.createProducer();
        // start the producer
        producer.start();
        // and let it go (processes the exchange by sending the email)
        producer.process(exchange);
    }

    /**
     *
     */
    private void onExit() {
        try {
            context.stop();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            System.exit(0);
        }
    }

    /**
     *
     */
    private void showFrame() {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.addWindowListener(new WindowEventHandler(context));

        JMenu file = new JMenu("File");
        file.setMnemonic('F');
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.setMnemonic('x');
        file.add(exitItem);

        // adding action listener to menu items
        exitItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onExit();
            }
        });

        JMenuBar bar = new JMenuBar();
        frame.setJMenuBar(bar);
        bar.add(file);

        frame.setSize(200, 200);
        frame.pack();
        frame.setVisible(true);
    }
}
