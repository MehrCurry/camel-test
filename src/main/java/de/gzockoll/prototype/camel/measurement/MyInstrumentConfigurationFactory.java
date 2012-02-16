/**
 * Created 13.02.2012
 * This code is copyright (c) 2004 PAYONE Gmbh & Co. KG.
 */
package de.gzockoll.prototype.camel.measurement;

import java.awt.Color;
import java.util.Arrays;
import java.util.Collection;

/**
 * @author Guido Zockoll
 * 
 */
public class MyInstrumentConfigurationFactory implements InstrumentConfigurationFactory {

    /*
     * (non-Javadoc)
     * 
     * @see de.gzockoll.prototype.camel.InstrumentConfigurationFactory#getInstrumentConfigurations()
     */
    @Override
    public Collection<InstrumentConfiguration> getInstrumentConfigurations() {
        return Arrays.asList(new InstrumentConfiguration[] {
                InstrumentConfiguration.builder().name("sekunden").title("Sekunden").unit("s").threshold(55).min(0)
                        .max(60).section(ColoredRange.create(new Color(0x00, 0xFF, 0x00, 0x80), 15, 30))
                        .area(ColoredRange.create(new Color(0xFF, 0x00, 0x00, 0x80), 30, 60)).build(),
                InstrumentConfiguration.builder().name("EDDH.TEMPERATUR").title("Temperatur").unit("°C").min(-50)
                        .max(50).area(ColoredRange.create(new Color(0xFF, 0x00, 0x00, 0x80), 30, 50)).build(),
                InstrumentConfiguration.builder().name("EDDH.WIND").title("Wind").unit("m/s").min(0).max(30).build(),
                InstrumentConfiguration.builder().name("EDDH.SICHT").title("Sicht").unit("km").min(0).max(15).build(),
                InstrumentConfiguration.builder().name("Messages.DURCHSATZ").title("Msg Load").unit("msg/s").min(0)
                        .max(15).build(),
                InstrumentConfiguration.builder().name("EDDH.LUFTDRUCK").title("Luftdruck").unit("hPa").min(910)
                        .max(1080).build()

        });
    }
}
