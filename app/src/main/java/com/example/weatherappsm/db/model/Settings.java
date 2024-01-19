package com.example.weatherappsm.db.model;

import android.annotation.SuppressLint;
import android.util.Log;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Settings {
    public enum TemperatureUnit {
        CELSIUS("°C", "°"),
        FAHRENHEIT("°F", "°");

        private final String unit;
        private final String shortUnit;

        TemperatureUnit(String c, String sc) {
            this.unit = c;
            this.shortUnit = sc;
        }

        public String getUnit() {
            return unit;
        }

        public static TemperatureUnit fromString(String text) {
            for (TemperatureUnit unit : TemperatureUnit.values()) {
                if (unit.unit.equalsIgnoreCase(text)) {
                    return unit;
                }
            }
            return null;
        }

        //example: 24.1°C
        public String format(String temperature) {
            Double formatted = new BigDecimal(temperature).setScale(1, RoundingMode.HALF_UP).doubleValue();
            return formatted + this.unit;
        }

        //example: 24.1°C
        public String format(double temperature) {
            Double formatted = new BigDecimal(temperature).setScale(1, RoundingMode.HALF_UP).doubleValue();
            return formatted + this.unit;
        }

        //example: 24°
        public String formatShort(String temperature) {
            Integer formatted = new BigDecimal(temperature).setScale(0, RoundingMode.HALF_UP).intValue();
            return formatted + this.shortUnit;
        }
    }

    public enum WindSpeedUnit {
        KILLOMETERS_PER_HOUR("km/h"),
        MILES_PER_HOUR("mph");

        private final String unit;

        WindSpeedUnit(String unit) {
            this.unit = unit;
        }

        public String getUnit() {
            return unit;
        }

        //example: 24.1 km/h
        public String format(double windSpeed) {
            Double formatted = new BigDecimal(windSpeed).setScale(1, RoundingMode.HALF_UP).doubleValue();
            return String.format("%s %s", formatted, this.unit);
        }

        public String format(String windSpeed) {
            Double formatted = new BigDecimal(windSpeed).setScale(1, RoundingMode.HALF_UP).doubleValue();
            return String.format("%s %s", formatted, this.unit);
        }

    }

    public enum HourFormat {
        TWELVE("hh:mm aa"),
        TWENTY_FOUR("HH:mm");

        @SuppressLint("ConstantLocale")
        private static final SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());

        private final SimpleDateFormat outputFormat;
        private final String format;

        HourFormat(String format) {
            this.format = format;
            this.outputFormat = new SimpleDateFormat(format, Locale.getDefault());
        }

        public String getFormat() {
            return format;
        }

        //example: 12:00 AM
        //example: 00:00
        public String format(String input) {
            if (input == null) {
                Log.e("HourFormat", "format: input is null");
                return "";
            }

            try {
                Date parsedDate = inputFormat.parse(input);
                if (parsedDate == null) {
                    Log.e("HourFormat", "format: parsedDate is null");
                    return "";
                }

                return outputFormat.format(parsedDate);
            } catch (Exception e) {
                Log.e("HourFormat", "format: ", e);
                e.printStackTrace();
                return input;
            }
        }
    }

    private TemperatureUnit temperatureUnit;
    private WindSpeedUnit windSpeedUnit;
    private HourFormat hourFormat;

    public TemperatureUnit getTemperatureUnit() {
        return temperatureUnit;
    }

    public void setTemperatureUnit(TemperatureUnit temperatureUnit) {
        this.temperatureUnit = temperatureUnit;
    }

    public WindSpeedUnit getWindSpeedUnit() {
        return windSpeedUnit;
    }

    public void setWindSpeedUnit(WindSpeedUnit windSpeedUnit) {
        this.windSpeedUnit = windSpeedUnit;
    }

    public HourFormat getHourFormat() {
        return hourFormat;
    }

    public void setHourFormat(HourFormat hourFormat) {
        this.hourFormat = hourFormat;
    }
}
