/**
 * Created 13.02.2012
 * This code is copyright (c) 2004 PAYONE Gmbh & Co. KG.
 */
package de.gzockoll.prototype.camel.measurement;

import java.util.Collection;

/**
 * @author Guido Zockoll
 * 
 */
public interface InstrumentConfigurationFactory {
    Collection<InstrumentConfiguration> getInstrumentConfigurations();
}
