package com.uk.trigtracker.Models;

import static java.lang.Float.parseFloat;

public class TrigPoint {
    private String lattitude;
    private String longitude;
    private String tpNum;
    private String name;
    private String type;
    private float heightM;
    private float heightFt;
    private String park;

    public TrigPoint(String[] tokens) {
        this.lattitude = (tokens[0]);
        this.longitude = (tokens[1]);
        this.tpNum = tokens[2];
        this.name = tokens[3];
        this.type = tokens[4];
        this.heightM = parseFloat(tokens[5]);
        this.heightFt = parseFloat(tokens[6]);
        this.park = tokens[7];
    }

}
