package com.example.weatherappsm.api;

import android.util.Pair;

import com.example.weatherappsm.objects.Settings;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/* loaded from: classes7.dex */
public class WeatherResponse {
    @SerializedName("current")
    private CurrentWeather current;
    @SerializedName("forecast")
    private ForecastWeather forecast;

    public CurrentWeather getCurrent() {
        return this.current;
    }

    public ForecastWeather getForecastWeather() {
        return this.forecast;
    }

    /* loaded from: classes7.dex */
    public static class ForecastWeather {
        @SerializedName("forecastday")
        private List<ForecastDay> forecastday;

        public List<ForecastDay> getForecastday() {
            return this.forecastday;
        }

        public static class ForecastDay {
            @SerializedName("date")
            private String date;
            @SerializedName("day")
            private Day day;
            @SerializedName("hour")
            private List<ForecastHour> hour;

            public String getDate() {
                return this.date;
            }

            public List<ForecastHour> getHour() {
                return this.hour;
            }

            public Day getDay() {
                return this.day;
            }

            public static class ForecastHour {
                @SerializedName("condition")
                private WeatherCondition condition;
                @SerializedName("temp_c")
                private double tempC;
                @SerializedName("time")
                private String time;
                @SerializedName("wind_kph")
                private double windKph;

                public String getTime() {
                    return this.time;
                }

                public double getTempC() {
                    return this.tempC;
                }

                public double getWindKph() {
                    return this.windKph;
                }

                public WeatherCondition getCondition() {
                    return this.condition;
                }
            }

            public static class Day {
                @SerializedName("maxtemp_c")
                private double maxtemp_c;
                @SerializedName("mintemp_c")
                private double mintemp_c;
                @SerializedName("mintemp_f")
                private double mintemp_f;
                @SerializedName("maxtemp_f")
                private double maxtemp_f;

                public double getMaxtemp_c() {
                    return this.maxtemp_c;
                }

                public double getMintemp_c() {
                    return this.mintemp_c;
                }

                public Pair<String, String> getMinMaxFormatted(Settings.TemperatureUnit unit) {
                    if (unit == Settings.TemperatureUnit.CELSIUS) {
                        return new Pair<>(unit.format(this.mintemp_c), unit.format(this.maxtemp_c));
                    } else {
                        return new Pair<>(unit.format(this.mintemp_f), unit.format(this.maxtemp_f));
                    }
                }
            }
        }
    }

    public static class CurrentWeather {
        @SerializedName("condition")
        private WeatherCondition condition;
        @SerializedName("is_day")
        private int isDay;
        @SerializedName("temp_c")
        private String temperatureC;
        @SerializedName("temp_f")
        private String temperatureF;
        @SerializedName("uv")
        private double uv;

        public String getTemperature(Settings.TemperatureUnit unit) {
            if (unit == Settings.TemperatureUnit.CELSIUS) {
                return this.temperatureC;
            } else {
                return this.temperatureF;
            }
        }

        public String getTemperatureFormatted(Settings.TemperatureUnit unit, boolean isShort) {
            if (unit == Settings.TemperatureUnit.CELSIUS) {
                return isShort ? unit.formatShort(this.temperatureC) : unit.format(this.temperatureC);
            } else {
                return isShort ? unit.formatShort(this.temperatureF) : unit.format(this.temperatureF);
            }
        }

        public boolean isDay() {
            return this.isDay == 1;
        }

        public WeatherCondition getCondition() {
            return this.condition;
        }

        public double getUv() {
            return this.uv;
        }
    }

    public static class WeatherCondition {
        @SerializedName("icon")
        private String icon;
        @SerializedName("text")
        private String text;

        public String getText() {
            return this.text;
        }

        public String getIcon() {
            return this.icon;
        }
    }
}