package de.gzockoll.prototype.camel.encashment.service;

import static org.joda.money.CurrencyUnit.EUR;

import java.util.Collection;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.camel.CamelContext;
import org.apache.camel.Endpoint;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Producer;
import org.apache.commons.lang.Validate;
import org.joda.money.Money;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.gzockoll.prototype.camel.encashment.EncashmentType;
import de.gzockoll.prototype.camel.encashment.entity.Customer;
import de.gzockoll.prototype.camel.encashment.entity.EncashmentEntry;
import de.gzockoll.prototype.camel.encashment.entity.Merchant;

@SuppressWarnings("javadoc")
@Service
@Transactional
public class EncashmentService {
    Logger logger = LoggerFactory.getLogger(EncashmentService.class);
    @PersistenceContext
    private EntityManager em;

    private CamelContext context;

    public EncashmentEntry processEncashmentOrder(Merchant m, Customer c, String text, Money amount) {
        EncashmentEntry entry = new EncashmentEntry(m, c, text, amount);
        em.persist(em);
        return entry;
    }

    public void processCredit(Merchant m, Customer c, String text, Money amount) {
    }

    public void processPayment(Merchant m, Customer c, String text, Money amount) {

    }

    public void onError(EncashmentEntry entry) {
        EncashmentEntry merged = em.merge(entry);
        merged.deliveryError();
        em.persist(merged);
    }

    public void onSuccess(EncashmentEntry entry) {
        EncashmentEntry merged = em.merge(entry);
        merged.successfulDelivered();
        em.persist(merged);
    }

    public void startProcessing() {
        logger.debug("Processing started!");
        if (em != null) {
            Collection<EncashmentEntry> entries = findAll();
            for (EncashmentEntry e : entries) {
                deliver(e);
            }
        } else
            logger.debug("No Entitymanager yet!");
    }

    private void deliver(EncashmentEntry e) {
        try {
            // create an exchange with a normal body and attachment to be
            // produced
            // as email
            Endpoint endpoint = context.getEndpoint("direct:input");

            // create the exchange with the mail message that is multipart with
            // a
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
        } catch (Exception ex) {
            logger.error("Delivery failed:", e);
        }
    }

    @SuppressWarnings("unchecked")
    private Collection<EncashmentEntry> findAll() {
        Validate.notNull(em);
        Query query = em.createQuery("SELECT e FROM EncashmentEntry e");
        return query.getResultList();

    }

    public void populateDatabase() {
        Merchant m = new Merchant("Zalando");
        em.persist(m);
        Customer c = new Customer("Vera Müller");
        em.persist(c);
        EncashmentEntry entry = new EncashmentEntry(m, c, "Schuhe", Money.ofMajor(EUR, 10));
        em.persist(entry);
    }
}
