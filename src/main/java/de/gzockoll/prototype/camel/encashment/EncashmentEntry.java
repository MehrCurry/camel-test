package de.gzockoll.prototype.camel.encashment;

import javax.persistence.OneToOne;

import org.joda.money.Money;

import de.gzockoll.prototype.camel.encashment.entity.Customer;
import de.gzockoll.prototype.camel.encashment.entity.Merchant;

public class EncashmentEntry {
	@OneToOne
	private Merchant merchant;
	@OneToOne
	private Customer customer;
	private String text;
	private Money money;

}
