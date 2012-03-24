/**
 * Created 09.02.2012
 * This code is copyright (c) 2004 PAYONE Gmbh & Co. KG.
 */
package de.gzockoll.prototype.camel;

import org.joda.time.DateTime;

import de.gzockoll.observation.Observation;
import de.gzockoll.observation.SimpleObservation;

public class SecondFactory {
	public static Observation getSeconds() {
		return new SimpleObservation("sekunden",
				new DateTime().getSecondOfMinute());
	}
}