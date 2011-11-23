/**
 * Created 22.11.2011
 * This code is copyright (c) 2004 PAYONE Gmbh & Co. KG.
 */
package de.gzockoll.prototype.camel;

import static org.hamcrest.core.Is.is;
import static org.joda.money.CurrencyUnit.EUR;
import static org.junit.Assert.assertThat;

import org.joda.money.Money;
import org.junit.After;
import org.junit.Test;

import de.payone.prototype.transactions.Account;

/**
 * @author Guido Zockoll
 * 
 */
public class AccountsTest {

    @After
    public void tearDown() {
        Accounts.clear();
    }

    /**
     * Test method for
     * {@link de.gzockoll.prototype.camel.Accounts#add(java.lang.String, de.payone.prototype.transactions.Account)}.
     */
    @Test
    public void testAdd() {
        Accounts.add("JUnit", new Account());
        assertThat(Accounts.exists("JUnit"), is(true));
    }

    /**
     * Test method for {@link de.gzockoll.prototype.camel.Accounts#find(java.lang.String)}.
     */
    @Test
    public void testFind() {
        Accounts.add("JUnit", new Account());
        assertThat(Accounts.exists("JUnit"), is(true));
        assertThat(Accounts.find("JUnit").getBalance(), is(Money.zero(EUR)));
    }

    /**
     * Test method for {@link de.gzockoll.prototype.camel.Accounts#exists(java.lang.String)}.
     */
    @Test
    public void testExists() {
        Accounts.add("JUnit", new Account());
        assertThat(Accounts.exists("JUnit"), is(true));
    }

    /**
     * Test method for {@link de.gzockoll.prototype.camel.Accounts#create(java.lang.String)}.
     */
    @Test
    public void testCreate() {
        Accounts.create("JUnit");
        assertThat(Accounts.exists("JUnit"), is(true));
    }

}
