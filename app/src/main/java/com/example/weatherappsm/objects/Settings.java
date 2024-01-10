package com.example.weatherappsm.objects;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Settings {
    public enum TemperatureUnit {
        CELSIUS("°C", "°"),
        FAHRENHEIT("°F", "°");

        private String unit;
        private String shortUnit;

        TemperatureUnit(String c, String sc) {
            this.unit = c;
            this.shortUnit = sc;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
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
    }

    private TemperatureUnit temperatureUnit;
    private WindSpeedUnit windSpeedUnit;

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
}
