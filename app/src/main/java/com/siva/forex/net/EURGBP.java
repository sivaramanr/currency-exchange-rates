package com.siva.forex.net;

import com.google.gson.annotations.SerializedName;

public class EURGBP {

    @SerializedName("rate")
    private double rate;

    @SerializedName("timestamp")
    private long timestamp;

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

}
