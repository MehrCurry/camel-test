/**
 * Created 09.02.2012
 * This code is copyright (c) 2004 PAYONE Gmbh & Co. KG.
 */
package de.gzockoll.prototype.camel;

import org.joda.time.DateTime;

import de.gzockoll.prototype.camel.observation.Observation;
import de.gzockoll.prototype.camel.observation.SimpleObservation;

class SecondFactory {
    public static Observation getSeconds() {
        return new SimpleObservation("sekunden", new DateTime().getSecondOfMinute());
    }
}