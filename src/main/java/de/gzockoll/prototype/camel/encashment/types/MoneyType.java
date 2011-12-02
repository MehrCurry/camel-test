package de.gzockoll.prototype.camel.encashment.types;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Currency;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.type.BigDecimalType;
import org.hibernate.type.CurrencyType;
import org.hibernate.type.Type;
import org.hibernate.usertype.CompositeUserType;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MoneyType implements CompositeUserType {
	private static Logger logger = LoggerFactory.getLogger(MoneyType.class);

	@Override
	public String[] getPropertyNames() {
		// ORDER IS IMPORTANT! it must match the order the columns are defined
		// in the property mapping
		return new String[] { "amount", "currency" };
	}

	@Override
	public Type[] getPropertyTypes() {
		return new Type[] { BigDecimalType.INSTANCE, CurrencyType.INSTANCE };
	}

	public Class getReturnedClass() {
		return Money.class;
	}

	@Override
	public Object getPropertyValue(Object component, int propertyIndex) {
		if (component == null) {
			return null;
		}

		final Money money = (Money) component;
		switch (propertyIndex) {
		case 0: {
			return money.getAmount();
		}
		case 1: {
			return money.getCurrencyUnit();
		}
		default: {
			throw new HibernateException("Invalid property index ["
					+ propertyIndex + "]");
		}
		}
	}

	@Override
	public void setPropertyValue(Object component, int propertyIndex,
			Object value) throws HibernateException {
		if (component == null) {
			return;
		}

		final Money money = (Money) component;
		switch (propertyIndex) {
		case 0: {
			money.withAmount((BigDecimal) value);
			break;
		}
		case 1: {
			money.withCurrencyUnit((CurrencyUnit) value);
			break;
		}
		default: {
			throw new HibernateException("Invalid property index ["
					+ propertyIndex + "]");
		}
		}
	}

	@Override
	public Object nullSafeGet(ResultSet rs, String[] names,
			SessionImplementor session, Object owner) throws SQLException {
		assert names.length == 2;
		BigDecimal amount = (BigDecimal) BigDecimalType.INSTANCE.get(rs,
				names[0], session); //
		Currency currency = (Currency) CurrencyType.INSTANCE.get(rs, names[1],
				session);
		return amount == null && currency == null ? null : Money.of(
				CurrencyUnit.of(currency), amount);
	}

	@Override
	public void nullSafeSet(PreparedStatement st, Object value, int index,
			SessionImplementor session) throws SQLException {
		if (value == null) {
			BigDecimalType.INSTANCE.set(st, null, index, session);
			CurrencyType.INSTANCE.set(st, null, index + 1, session);
		} else {
			final Money money = (Money) value;
			BigDecimalType.INSTANCE.set(st, money.getAmount(), index, session);
			CurrencyType.INSTANCE.set(st, money.getCurrencyUnit().toCurrency(),
					index + 1, session);
		}
	}

	@Override
	public Object assemble(Serializable arg0, SessionImplementor arg1,
			Object arg2) throws HibernateException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object deepCopy(Object arg0) throws HibernateException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Serializable disassemble(Object arg0, SessionImplementor arg1)
			throws HibernateException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean equals(Object x, Object y) throws HibernateException {
		if (x == y)
			return true;
		return x == null ? false : x.equals(y);
	}

	@Override
	public int hashCode(Object obj) throws HibernateException {
		return obj == null ? 0 : obj.hashCode();
	}

	@Override
	public boolean isMutable() {
		return false;
	}

	@Override
	public Object replace(Object arg0, Object arg1, SessionImplementor arg2,
			Object arg3) throws HibernateException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Class returnedClass() {
		return Money.class;
	}
}
