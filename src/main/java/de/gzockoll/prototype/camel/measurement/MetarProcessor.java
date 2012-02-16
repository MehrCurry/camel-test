package de.gzockoll.prototype.camel.measurement;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import net.sf.jweather.metar.Metar;
import net.sf.jweather.metar.MetarParser;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.gzockoll.prototype.camel.observation.Measurement;
import de.gzockoll.prototype.camel.observation.NamedSubject;
import de.gzockoll.prototype.camel.observation.NumberQuantity;
import de.gzockoll.prototype.camel.observation.Observation;
import static de.gzockoll.prototype.camel.measurement.MetarMesswerte.*;
import static de.gzockoll.prototype.camel.observation.Units.*;


public class MetarProcessor implements Processor {
    private static final Logger logger = LoggerFactory.getLogger(MetarProcessor.class);

    @Override
    public void process(Exchange ex) throws Exception {
        String data = IOUtils.toString((InputStream) ex.getIn().getBody());
        logger.debug("Data: " + data);
        Metar metar = MetarParser.parseRecord(data);
        logger.debug("Metar: " + metar);
        List<Observation> list = new ArrayList<Observation>();
        String station = metar.getStationID();
        list.add(new Measurement(station, TEMPERATUR, DEGREE_CELSIUS, metar.getTemperatureMostPreciseInCelsius()));
        list.add(new Measurement(station, LUFTDRUCK, HECTOPASCAL, metar.getPressureInHectoPascals()));
        list.add(new Measurement(station, WIND, METER_PER_SECOND, metar.getWindSpeedInMPS()));
        list.add(new Measurement(station, WINDRICHTUNG, DEGREE, metar.getWindDirection()));

        list.add(new Measurement(station, TAUPUNKT, DEGREE_CELSIUS, metar.getDewPointPreciseInCelsius()));
        list.add(new Measurement(station, SICHT, KILOMETER, metar.getVisibilityInKilometers()));
        ex.getOut().setBody(list);
    }
}
