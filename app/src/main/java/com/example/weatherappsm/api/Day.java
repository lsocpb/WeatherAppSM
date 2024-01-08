package com.example.weatherappsm.api;

import com.google.gson.annotations.SerializedName;

public class Day {
    @SerializedName("maxtemp_c")
    private double maxtemp_c;

    @SerializedName("mintemp_c")
    private double mintemp_c;

    public double getMaxtemp_c() {
        return maxtemp_c;
    }

    public void setMaxtemp_c(double maxtemp_c) {
        this.maxtemp_c = maxtemp_c;
    }

    public double getMintemp_c() {
        return mintemp_c;
    }

    public void setMintemp_c(double mintemp_c) {
        this.mintemp_c = mintemp_c;
    }
}
