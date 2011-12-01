package de.gzockoll.prototype.camel;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.commons.lang.Validate;
import org.joda.time.DateTime;

import de.payone.prototype.transactions.Account;
import de.payone.prototype.transactions.AccountingTransaction;
import de.payone.prototype.transactions.CreditBooking;
import de.payone.prototype.transactions.DebitBooking;

/**
 * @author Guido Zockoll
 * 
 */
public class AccountingProcessor implements Processor {
    @Override
    public void process(Exchange e) {
        Validate.isTrue(e.getIn().getBody() instanceof Ueberweisung);
        Ueberweisung to = (Ueberweisung) e.getIn().getBody();

        AccountingTransaction tx = new AccountingTransaction(DateTime.now());
        tx.add(new CreditBooking(findAccount("Income"), to.getValue()));
        tx.add(new DebitBooking(findAccount(to.getName()), to.getValue()));
        tx.post();
    }

    private Account findAccount(String key) {
        return Accounts.findOrCreate(key);
    }
}