package com.uk.trigtracker.Models;


import android.os.Parcel;
import android.os.Parcelable;

public class TrigPoint implements Parcelable {
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
        if(tokens.length == 8) {
            this.park = tokens[7];
        }
    }

    protected TrigPoint(Parcel in) {
        if (in.readByte() == 0) {
            latitude = null;
        } else {
            latitude = in.readDouble();
        }
        if (in.readByte() == 0) {
            longitude = null;
        } else {
            longitude = in.readDouble();
        }
        tpNum = in.readString();
        name = in.readString();
        type = in.readString();
        if (in.readByte() == 0) {
            heightM = null;
        } else {
            heightM = in.readDouble();
        }
        if (in.readByte() == 0) {
            heightFt = null;
        } else {
            heightFt = in.readDouble();
        }
        park = in.readString();
    }

    public static final Creator<TrigPoint> CREATOR = new Creator<TrigPoint>() {
        @Override
        public TrigPoint createFromParcel(Parcel in) {
            return new TrigPoint(in);
        }

        @Override
        public TrigPoint[] newArray(int size) {
            return new TrigPoint[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (latitude == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(latitude);
        }
        if (longitude == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(longitude);
        }
        dest.writeString(tpNum);
        dest.writeString(name);
        dest.writeString(type);
        if (heightM == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(heightM);
        }
        if (heightFt == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(heightFt);
        }
        dest.writeString(park);
    }
}
