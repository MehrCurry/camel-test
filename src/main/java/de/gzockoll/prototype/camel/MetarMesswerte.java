/**
 * Created 08.02.2012
 * This code is copyright (c) 2004 PAYONE Gmbh & Co. KG.
 */
package de.gzockoll.prototype.camel;

import de.gzockoll.prototype.camel.observation.Measurement;
import de.gzockoll.prototype.camel.observation.Observation;
import de.gzockoll.prototype.camel.observation.PhanomenonType;
import de.gzockoll.prototype.camel.observation.Quantity;
import de.gzockoll.prototype.camel.observation.Subject;

/**
 * @author Guido Zockoll
 * 
 */
public enum MetarMesswerte implements PhanomenonType {
    TEMPERATUR, LUFTDRUCK, TAUPUNKT, SICHT, WIND, WINDRICHTUNG;

    public Observation observation(Subject subject, Quantity quantity) {
        return new Measurement(subject, this, quantity);
    }
}
