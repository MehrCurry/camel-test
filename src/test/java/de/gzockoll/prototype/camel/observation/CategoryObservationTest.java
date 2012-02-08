package de.gzockoll.prototype.camel.observation;

import static org.hamcrest.core.Is.*;
import static org.junit.Assert.*;

import org.junit.Test;

public class CategoryObservationTest {

	@Test
	public void test() {
		Observation o = BloodGroups.A.observation(new Subject() {

			@Override
			public String getName() {
				return "JUnit";
			}
		});

		assertThat(o.getValue() == BloodGroups.A, is(true));
	}

	private static enum PhanomenonTypes implements PhanomenonType {
		BLOODGROUP;
	}

	private static enum BloodGroups implements Phanomenon {
		A(PhanomenonTypes.BLOODGROUP), B(PhanomenonTypes.BLOODGROUP), NULL(
				PhanomenonTypes.BLOODGROUP), AB(PhanomenonTypes.BLOODGROUP);

		private PhanomenonType type;

		private BloodGroups(PhanomenonType type) {
			this.type = type;
		}

		public Observation observation(Subject subject) {
			return new CategoryObservation(subject, this);
		}

		@Override
		public PhanomenonType getType() {
			return type;
		}
	}
}
