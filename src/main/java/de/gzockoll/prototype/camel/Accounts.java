/**
 * Created 22.11.2011
 * This code is copyright (c) 2004 PAYONE Gmbh & Co. KG.
 */
package de.gzockoll.prototype.camel;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.Validate;

import de.payone.prototype.transactions.Account;

/**
 * @author Guido Zockoll
 * 
 */
public class Accounts {
    private static final Map<String, Account> accounts = new HashMap<String, Account>();

    public static void add(String key, Account value) {
        Validate.isTrue(!exists(key));
        accounts.put(key, value);
        Validate.isTrue(exists(key));
    }

    public static Account find(String key) {
        return accounts.get(key);
    }

    public static boolean exists(String key) {
        return find(key) != null;
    }

    public static Account create(String key) {
        Validate.isTrue(!exists(key));
        Account a = new Account();
        add(key, a);
        Validate.isTrue(exists(key));
        return a;
    }

    /**
     *
     */
    public static void clear() {
        accounts.clear();
    }

    /**
     * @param key
     * @return
     */
    public static Account findOrCreate(String key) {
        if (!exists(key))
            return create(key);
        else
            return find(key);
    }
}
