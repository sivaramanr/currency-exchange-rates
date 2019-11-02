package com.siva.forex.net;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ExchangeRates {

    @SerializedName("rates")
    @Expose
    private Rates rates;

    @SerializedName("code")
    @Expose
    private int code;

    public Rates getRates() {
        return rates;
    }

    public void setRates(Rates rates) {
        this.rates = rates;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public static class Rates {

        @SerializedName("EURGBP")
        @Expose
        private EURGBP EURGBP;

        @SerializedName("USDEUR")
        @Expose
        private USDEUR USDEUR;

        public com.siva.forex.net.EURGBP getEURGBP() {
            return EURGBP;
        }

        public void setEURGBP(com.siva.forex.net.EURGBP EURGBP) {
            this.EURGBP = EURGBP;
        }

        public com.siva.forex.net.USDEUR getUSDEUR() {
            return USDEUR;
        }

        public void setUSDEUR(com.siva.forex.net.USDEUR USDEUR) {
            this.USDEUR = USDEUR;
        }

    }

}
