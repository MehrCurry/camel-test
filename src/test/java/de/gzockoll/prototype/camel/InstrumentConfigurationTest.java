/**
 * Created 08.02.2012
 * This code is copyright (c) 2004 PAYONE Gmbh & Co. KG.
 */
package de.gzockoll.prototype.camel;

import static org.junit.Assert.*;

import org.junit.Test;
import static org.junit.matchers.JUnitMatchers.*;

/**
 * @author Guido Zockoll
 * 
 */
public class InstrumentConfigurationTest {

    /**
     * Test method for {@link de.gzockoll.prototype.camel.InstrumentConfiguration#builder()}.
     */
    @Test
    public void testBuilder() {
        InstrumentConfiguration conf = InstrumentConfiguration.builder().name("JUnit").unit("m/s").build();

        String s = conf.toString();
        assertThat(s, containsString("name=JUnit"));
        assertThat(s, containsString("unit=m/s"));
    }

}
