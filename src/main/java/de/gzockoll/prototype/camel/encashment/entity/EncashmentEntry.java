package de.gzockoll.prototype.camel.encashment.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;

import org.apache.camel.dataformat.bindy.annotation.CsvRecord;
import org.apache.camel.dataformat.bindy.annotation.DataField;
import org.hibernate.annotations.Columns;
import org.hibernate.annotations.Type;
import org.joda.money.Money;

@SuppressWarnings("javadoc")
@Entity
@NamedQueries({
		@NamedQuery(name = EncashmentEntry.FIND_BY_STATUS, query = "Select e from EncashmentEntry e where e.status = :status"),
		@NamedQuery(name = EncashmentEntry.FIND_BY_CUSTOMER_AND_MERCHANT, query = "Select e from EncashmentEntry e where e.customer = :customer and e.merchant = :merchant") })
@CsvRecord(separator = ",", generateHeaderColumns = true)
public class EncashmentEntry extends AbstractEntity {
	public static final String FIND_BY_STATUS = "EncashmentEntry.findByStatus";
	public static final String FIND_BY_CUSTOMER_AND_MERCHANT = "EncashmentEntry.findByCustomerAndMerchant";

	@OneToOne
	@DataField(pos = 1)
	private Merchant merchant;
	@OneToOne
	@DataField(pos = 2)
	private Customer customer;
	@DataField(pos = 3)
	private String text;
	@Type(type = "de.gzockoll.prototype.camel.encashment.types.MoneyType")
	@Columns(columns = { @Column(name = "amount"), @Column(name = "currency") })
	@DataField(pos = 4)
	private Money money;
	@Enumerated(EnumType.STRING)
	@DataField(pos = 5)
	private EncashmentStatus status = EncashmentStatus.NEW;
	@DataField(pos = 6)
	private int tries = 0;
	@Enumerated(EnumType.STRING)
	@DataField(pos = 7)
	private EncashmentType type;

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
	}

	public void deliveryError() {
		tries++;
		if (tries < 3)
			status = EncashmentStatus.ERROR;
		else
			status = EncashmentStatus.FAILED;

	}

	public void successfulDelivered() {
		status = EncashmentStatus.DELIVERED;
	}
}
