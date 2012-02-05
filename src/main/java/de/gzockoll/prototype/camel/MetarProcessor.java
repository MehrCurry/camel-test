package de.gzockoll.prototype.camel;

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

public class MetarProcessor implements Processor {
	private static final Logger logger = LoggerFactory
			.getLogger(MetarProcessor.class);

	@Override
	public void process(Exchange ex) throws Exception {
		String data = IOUtils.toString((InputStream) ex.getIn().getBody());
		logger.debug("Data: " + data);
		Metar metar = MetarParser.parseRecord(data);
		logger.debug("Metar: " + metar);
		List<Observation> list = new ArrayList<Observation>();
		String station = metar.getStationID();
		list.add(new Observation(station + ".Temperatur", metar
				.getTemperatureInCelsius()));
		list.add(new Observation(station + ".Luftdruck", metar
				.getPressureInHectoPascals()));
		list.add(new Observation(station + ".Wind", metar.getWindSpeedInMPS()));
		list.add(new Observation(station + ".Windrichtung", metar
				.getWindDirection()));
		ex.getOut().setBody(list);
	}
}
