package com.uk.trigtracker.Models;


public class TrigPoint{
    private Double latitude;
    private Double longitude;
    private String tpNum;
    private String name;
    private String type;
    private Double heightM;
    private Double heightFt;
    private String park;

    public TrigPoint(String[] tokens) {
        // TODO: Add checks for missing fields
        this.latitude = Double.parseDouble(tokens[0]);
        this.longitude = Double.parseDouble(tokens[1]);
        this.tpNum = tokens[2];
        this.name = tokens[3];
        this.type = tokens[4];
        this.heightM = Double.parseDouble(tokens[5]);
        this.heightFt = Double.parseDouble(tokens[6]);
        this.park = tokens[7];
    }

    public Double getlatitude() {
        return latitude;
    }

    public void setlatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getTpNum() {
        return tpNum;
    }

    public void setTpNum(String tpNum) {
        this.tpNum = tpNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getHeightM() {
        return heightM;
    }

    public void setHeightM(Double heightM) {
        this.heightM = heightM;
    }

    public Double getHeightFt() {
        return heightFt;
    }

    public void setHeightFt(Double heightFt) {
        this.heightFt = heightFt;
    }

    public String getPark() {
        return park;
    }

    public void setPark(String park) {
        this.park = park;
    }

}
