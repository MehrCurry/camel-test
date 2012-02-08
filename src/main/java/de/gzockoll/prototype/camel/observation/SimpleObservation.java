/**
 * Created 08.02.2012
 * This code is copyright (c) 2004 PAYONE Gmbh & Co. KG.
 */
package de.gzockoll.prototype.camel.observation;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/**
 * @author Guido Zockoll
 * 
 */
public class SimpleObservation extends Observation {
    private String key;
    private Number value;

    /**
     * Create a new SimpleObservation.
     * 
     * @param subject
     * @param key
     * @param value
     */
    public SimpleObservation(String key, Number value) {
        super(null);
        this.key = key;
        this.value = value;
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.gzockoll.prototype.camel.observation.Observation#toJSON()
     */
    @Override
    public String toJSON() {

        try {
            return new JSONObject().put("key", key).put("value", value).toString();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.gzockoll.prototype.camel.observation.Observation#getValue()
     */
    @Override
    public Object getValue() {
        return value;
    }
}
