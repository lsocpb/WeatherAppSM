package com.example.weatherappsm.objects;

public class Settings {
    public enum TemperatureUnit {
        CELSIUS("C"),
        FAHRENHEIT("F");

        private String unit;

        TemperatureUnit(String c) {
            this.unit = c;
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
    }

    public enum WindSpeedUnit {
        KILLOMETERS_PER_HOUR("kph"),
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
