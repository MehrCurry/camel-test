package de.gzockoll.prototype.camel.encashment.service;

import static org.joda.money.CurrencyUnit.EUR;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import de.gzockoll.prototype.camel.encashment.MessageHandler;
import de.gzockoll.prototype.camel.encashment.entity.Customer;
import de.gzockoll.prototype.camel.encashment.entity.EncashmentEntry;
import de.gzockoll.prototype.camel.encashment.entity.EncashmentStatus;
import de.gzockoll.prototype.camel.encashment.entity.EncashmentType;
import de.gzockoll.prototype.camel.encashment.entity.Merchant;

@SuppressWarnings("javadoc")
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class EncashmentService {
    Logger logger = LoggerFactory.getLogger(EncashmentService.class);
    @PersistenceContext
    private EntityManager em;
    @Autowired
    private CamelContext context;

    @Autowired
    private MessageHandler msgHandler;

    public void setMsgHandler(MessageHandler msgHandler) {
        this.msgHandler = msgHandler;
    }

    public EncashmentEntry processEncashmentOrder(Merchant m, Customer c, String text, Money amount) {
        EncashmentEntry entry = new EncashmentEntry(em.merge(m), em.merge(c), text, amount, EncashmentType.ORDER);
        em.persist(entry);
        return entry;
    }

    public EncashmentEntry processCredit(Merchant m, Customer c, String text, Money amount) {
        EncashmentEntry entry = new EncashmentEntry(em.merge(m), em.merge(c), text, amount, EncashmentType.CREDIT);
        em.persist(entry);
        return entry;
    }

    public EncashmentEntry processPayment(Merchant m, Customer c, String text, Money amount) {
        EncashmentEntry entry = new EncashmentEntry(em.merge(m), em.merge(c), text, amount, EncashmentType.PAYMENT);
        em.persist(entry);
        return entry;

    }

    public void onError(byte[] body, Exchange exchange) {
        String message = "Error delivering: " + exchange;
        logger.warn(message);
        if (msgHandler != null)
            msgHandler.showError(message, exchange.getException());
        EncashmentEntry entry = em.find(EncashmentEntry.class, exchange.getProperty("encashmentId"));
        Validate.notNull(entry);
        entry.deliveryError();
        em.persist(entry);
    }

    public void onSuccess(byte[] body, Exchange exchange) {
        logger.debug("Successful delivered: " + exchange);
        EncashmentEntry entry = em.find(EncashmentEntry.class, exchange.getProperty("encashmentId"));
        Validate.notNull(entry);
        entry.successfulDelivered();
        em.persist(entry);
    }

    public void startProcessing() {
        logger.debug("Processing started!");
        Validate.notNull(em);
        Query query = em.createQuery("SELECT e FROM EncashmentEntry e WHERE e.status = 'NEW' OR e.status = 'ERROR' ");
        Collection<EncashmentEntry> entries = query.getResultList();
        for (EncashmentEntry e : entries) {
            if (e.getStatus() != EncashmentStatus.DELIVERED)
                deliver(e);
        }
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
            exchange.setProperty("TYPE", e.getType().name());
            exchange.setProperty("encashmentId", e.getId());
            Message in = exchange.getIn();
            in.setBody(e);

            // create a producer that can produce the exchange (= send the mail)
            Producer producer = endpoint.createProducer();
            // start the producer
            producer.start();
            // and let it go (processes the exchange by sending the email)
            producer.process(exchange);
            e.setStatus(EncashmentStatus.PROCESSING);
            em.persist(e);
        } catch (Exception ex) {
            logger.error("Delivery failed:", e);
            e.deliveryError();
        }
    }

    @SuppressWarnings("unchecked")
    private Collection<EncashmentEntry> findAll() {
        Validate.notNull(em);
        Query query = em.createQuery("SELECT e FROM EncashmentEntry e");
        return query.getResultList();

    }

    public void populateDatabase() {
        Merchant m = getMerchant("Zalando");
        Customer c = getCustomer("Vera Müller");
        processEncashmentOrder(m, c, "Schuhe", Money.ofMajor(EUR, 10));
        processCredit(m, c, "Schuhe", Money.ofMajor(EUR, 10));
        processPayment(m, c, "Schuhe", Money.ofMajor(EUR, 10));
    }

    private Customer getCustomer(String name) {
        Query query = em.createNamedQuery(Customer.FIND_BY_NAME);
        Customer c;
        try {
            c = (Customer) query.setParameter("name", name).getSingleResult();
        } catch (NoResultException e) {
            c = new Customer(name);
            em.persist(c);
        }
        return c;
    }

    private Merchant getMerchant(String name) {
        Query query = em.createNamedQuery(Merchant.FIND_BY_NAME);
        Merchant m;
        try {
            m = (Merchant) query.setParameter("name", name).getSingleResult();
        } catch (NoResultException e) {
            m = new Merchant(name);
            em.persist(m);
        }
        return m;
    }
}
