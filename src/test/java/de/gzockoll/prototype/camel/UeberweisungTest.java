package de.gzockoll.prototype.camel;

import static org.junit.Assert.*;
import static org.hamcrest.core.Is.*;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.junit.Test;

public class UeberweisungTest {

	@Test(expected=IllegalArgumentException.class)
	public void testIncomplete1() {
		Ueberweisung u=new Ueberweisung.Builder().name("JUnit").build();
	}
	@Test(expected=IllegalArgumentException.class)
	public void testIncomplete2() {
		Ueberweisung u=new Ueberweisung.Builder().name("JUnit").zweck("Bla").build();
	}
	@Test(expected=IllegalArgumentException.class)
	public void testIncomplete3() {
		Ueberweisung u=new Ueberweisung.Builder().name("JUnit").betrag("10", "EUR").build();
	}
	@Test(expected=IllegalArgumentException.class)
	
	public void testIncomplete4() {
		Ueberweisung u=new Ueberweisung.Builder().name(null).build();
	}

	public void testIncomplete5() {
		Ueberweisung u=new Ueberweisung.Builder().name("").build();
	}

	@Test
	public void testComplete() {
		Ueberweisung u=new Ueberweisung.Builder().name("JUnit").betrag("10", "EUR").zweck("Bla").build();
		assertThat(u.getText(),is("Bla"));
		assertThat(u.getValue(),is(Money.ofMajor(CurrencyUnit.EUR, 10)));
		assertThat(u.getName(),is("JUnit"));
	}

}
