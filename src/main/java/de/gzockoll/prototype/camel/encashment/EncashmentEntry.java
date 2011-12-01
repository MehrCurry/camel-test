package de.gzockoll.prototype.camel.encashment;

import javax.persistence.OneToOne;

import org.joda.money.Money;

public class EncashmentEntry {
	@OneToOne
	private Merchant merchant;
	@OneToOne
	private Customer customer;
	private String text;
	private Money money;

}
