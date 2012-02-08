package de.gzockoll.prototype.camel.observation;

import static org.hamcrest.core.Is.*;
import static org.junit.Assert.*;

import org.junit.Test;

public class MeasurementTest {

	@Test
	public void test() {
		Observation o = PhaenomenonTypes.TEMPERATURE.observation(new Subject() {

			@Override
			public String getName() {
				return "JUnit";
			}
		}, new NumberQuantity(Units.DEGREE_CELSIUS, 0.0));
		Quantity q = (Quantity) o.getValue();
		assertThat((Double) q.getValue(), is(0.0));
	}

	private static enum PhaenomenonTypes implements PhanomenonType {
		TEMPERATURE;

		public Observation observation(Subject subject, Quantity quantity) {
			return new Measurement(subject, this, quantity);
		}
	}

	private static enum Units implements Unit {
		DEGREE_CELSIUS;
	}
}
