package de.gzockoll.prototype.camel.encashment.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Columns;
import org.hibernate.annotations.Type;
import org.joda.money.Money;

@SuppressWarnings("javadoc")
@Entity
@NamedQueries({
		@NamedQuery(name = EncashmentEntry.FIND_BY_STATUS, query = "Select e from EncashmentEntry e where e.status = :status"),
		@NamedQuery(name = EncashmentEntry.FIND_BY_CUSTOMER_AND_MERCHANT, query = "Select e from EncashmentEntry e where e.customer = :customer and e.merchant = :merchant") })
public class EncashmentEntry extends AbstractEntity {
	public static final String FIND_BY_STATUS = "EncashmentEntry.findByStatus";
	public static final String FIND_BY_CUSTOMER_AND_MERCHANT = "EncashmentEntry.findByCustomerAndMerchant";

	@OneToOne
	private Merchant merchant;
	@OneToOne
	private Customer customer;
	private String text;
	@Type(type = "de.gzockoll.prototype.camel.encashment.types.MoneyType")
	@Columns(columns = { @Column(name = "amount"), @Column(name = "currency") })
	private Money money;
	@Enumerated(EnumType.STRING)
	private EncashmentStatus status = EncashmentStatus.NEW;
	private int tries = 0;
	@Enumerated(EnumType.STRING)
	private EncashmentType type;

	@Temporal(TemporalType.TIMESTAMP)
	private Date stateChanged = new Date();

	public EncashmentEntry() {
		// TODO Auto-generated constructor stub
	}

	public EncashmentEntry(Merchant merchant, Customer customer, String text,
			Money money, EncashmentType type) {
		super();
		this.merchant = merchant;
		this.customer = customer;
		this.text = text;
		this.money = money;
		this.type = type;
	}

	public EncashmentType getType() {
		return type;
	}

	public Merchant getMerchant() {
		return merchant;
	}

	public Customer getCustomer() {
		return customer;
	}

	public String getText() {
		return text;
	}

	public Money getMoney() {
		return money;
	}

	public EncashmentStatus getStatus() {
		return status;
	}

	public void setStatus(EncashmentStatus status) {
		this.status = status;
		stateChanged = new Date();
	}

	public void deliveryError() {
		tries++;
		if (tries < 3)
			setStatus(EncashmentStatus.ERROR);
		else
			setStatus(EncashmentStatus.FAILED);

	}

	public void successfulDelivered() {
		setStatus(EncashmentStatus.DELIVERED);
	}
}
