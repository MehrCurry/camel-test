/**
 * Created 08.02.2012
 * This code is copyright (c) 2004 PAYONE Gmbh & Co. KG.
 */
package de.gzockoll.prototype.camel.observation;

import static org.junit.matchers.JUnitMatchers.*;
import static org.junit.Assert.*;

import org.junit.Test;

/**
 * @author Guido Zockoll
 * 
 */
public class SimpleObservationTest {

    /**
     * Test method for {@link de.gzockoll.prototype.camel.observation.SimpleObservation#toJSON()}.
     */
    @Test
    public void testToJSON() {
        SimpleObservation o = new SimpleObservation("JUnit", 123);
        String json = o.toJSON();
        assertThat(json, containsString("\"key\":\"JUnit\""));
        assertThat(json, containsString("\"value\":123"));
    }

}
