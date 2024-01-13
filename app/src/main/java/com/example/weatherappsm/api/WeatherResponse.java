package com.example.weatherappsm.api;

import android.util.Pair;

import com.example.weatherappsm.db.model.Settings;
import com.google.gson.annotations.SerializedName;

import java.util.List;

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

            @SerializedName("astro")
            private Astro astro;

            public String getDate() {
                return this.date;
            }

            public List<ForecastHour> getHour() {
                return this.hour;
            }

            public Day getDay() {
                return this.day;
            }

            public Astro  getAstro(){
                return this.astro;
            }


            public static class ForecastHour {
                @SerializedName("condition")
                private WeatherCondition condition;
                @SerializedName("temp_c")
                private double tempC;
                @SerializedName("temp_f")
                private double tempF;
                @SerializedName("time")
                private String time;
                @SerializedName("wind_kph")
                private double windKph;
                @SerializedName("wind_mph")
                private double windMph;

                public String getWindSpeedFormatted(Settings.WindSpeedUnit unit) {
                    if (unit == Settings.WindSpeedUnit.KILLOMETERS_PER_HOUR) {
                        return unit.format(this.windKph);
                    } else {
                        return unit.format(this.windMph);
                    }
                }

                public String getTemperatureFormatted(Settings.TemperatureUnit unit) {
                    if (unit == Settings.TemperatureUnit.CELSIUS) {
                        return unit.format(this.tempC);
                    } else {
                        return unit.format(this.tempF);
                    }
                }

                public String getTime(Settings.HourFormat hourFormat) {
                    return hourFormat.format(this.time);
                }

                public WeatherCondition getCondition() {
                    return this.condition;
                }
            }

            public static class Astro{
                @SerializedName("sunrise")
                private String sunrise;
                @SerializedName("sunset")
                private String sunset;

                public String getSunrise(Settings.HourFormat hourFormat){
                    return hourFormat.format(this.sunrise);
                }
                public String getSunset(Settings.HourFormat hourFormat){
                    return hourFormat.format(this.sunset);
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
        @SerializedName("wind_mph")
        private double windMph;
        @SerializedName("wind_kph")
        private double windKph;

        @SerializedName("wind_dir")
        private String windDir;

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

        public String getWindSpeedFormatted(Settings.WindSpeedUnit unit) {
            if (unit == Settings.WindSpeedUnit.KILLOMETERS_PER_HOUR) {
                return unit.format(this.windKph);
            } else {
                return unit.format(this.windMph);
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

        public String getWindDir(){
            return this.windDir;
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