/**
 * Created 08.02.2012
 * This code is copyright (c) 2004 PAYONE Gmbh & Co. KG.
 */
package de.gzockoll.prototype.camel.observation;

/**
 * @author Guido Zockoll
 * 
 */
public enum Units implements Unit {
    ONE("1"), PER_SECOND("1/s"), DEGREE_CELSIUS("°C"), METER_PER_SECOND("m/s"), HECTOPASCAL("hPA"), DEGREE("°"), KILOMETER(
            "km");

    private String unitString;

    /**
     * Create a new Units.
     * 
     * @param unitString
     */
    private Units(String unitString) {
        this.unitString = unitString;
    }
}
