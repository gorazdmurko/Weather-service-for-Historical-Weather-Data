package si.projektna.unit29.adapter;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Arrays;
import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherData {

    @JsonIgnoreProperties(ignoreUnknown = true)
    static class Condition {
        private String text;
        private String icon;
        private int code;

        public Condition() {
        }

        public Condition(String text, String icon, int code) {
            this.text = text;
            this.icon = icon;
            this.code = code;
        }

        public String getText() { return text; }
        public String getIcon() { return icon; }
        public int getCode() { return code; }

        @Override
        public String toString() {
            return "Condition{" +
                    "text='" + text + '\'' +
                    ", icon='" + icon + '\'' +
                    ", code=" + code +
                    '}';
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    static class Astro {
        private String sunrise;
        private String sunset;

        public Astro() {
        }

        public Astro(String sunrise, String sunset) {
            this.sunrise = sunrise;
            this.sunset = sunset;
        }

        public String getSunrise() { return sunrise; }
        public String getSunset() { return sunset; }

        @Override
        public String toString() {
            return "Astro{" +
                    "sunrise='" + sunrise + '\'' +
                    ", sunset='" + sunset + '\'' +
                    '}';
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    static class Day {
        private Condition condition;
        private float maxtemp_c;
        private float mintemp_c;

        public Day() {
        }

        public Day(Condition condition, float maxtemp_c, float mintemp_c) {
            this.condition = condition;
            this.maxtemp_c = maxtemp_c;
            this.mintemp_c = mintemp_c;
        }

        public Condition getCondition() { return condition; }
        public float getMaxtemp_c() { return maxtemp_c; }
        public float getMintemp_c() { return mintemp_c; }

        @Override
        public String toString() {
            return "Day{" +
                    "condition=" + condition +
                    ", maxtemp_c=" + maxtemp_c +
                    ", mintemp_c=" + mintemp_c +
                    '}';
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    static class Hour {
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
        private Date time;
        private float temp_c;
        private Condition condition;
        private float pressure_mb;
        private int humidity;

        public Hour() {
        }

        public Hour(Date time, float temp_c, Condition condition, float pressure_mb, int humidity) {
            this.time = time;
            this.temp_c = temp_c;
            this.condition = condition;
            this.pressure_mb = pressure_mb;
            this.humidity = humidity;
        }

        public Date getTime() { return time; }
        public float getTemp_c() { return temp_c; }
        public Condition getCondition() { return condition; }
        public float getPressure_mb() { return pressure_mb; }
        public int getHumidity() { return humidity; }

        @Override
        public String toString() {
            return "Hour{" +
                    "time=" + time +
                    ", temp_c=" + temp_c +
                    ", condition=" + condition +
                    ", pressure_mb=" + pressure_mb +
                    ", humidity=" + humidity +
                    '}';
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    static class Forecastday {
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        private Date date;
        private Day day;
        private Astro astro;
        private Hour[] hour;

        public Forecastday() {
        }

        public Forecastday(Date date, Day day, Hour[] hour) {
            this.date = date;
            this.day = day;
            this.hour = hour;
        }

        public Date getDate() { return date; }
        public Day getDay() { return day; }
        public Astro getAstro() { return astro; }
        public Hour[] getHour() { return hour; }

        @Override
        public String toString() {
            return "Forecastday{" +
                    "date=" + date +
                    ", day=" + day +
                    ", astro=" + astro +
                    ", hour=" + Arrays.toString(hour) +
                    '}';
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    static class Forecast {
        private Forecastday[] forecastday;

        public Forecast() {
        }

        public Forecast(Forecastday[] forecastday) {
            this.forecastday = forecastday;
        }

        public Forecastday[] getForecastday() { return forecastday; }

        @Override
        public String toString() {
            return "Forecast{" +
                    "forecastday=" + Arrays.toString(forecastday) +
                    '}';
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    static class Location {
        private String name;
        private String region;
        private String country;

        public Location() {
        }

        public Location(String name, String region, String country) {
            this.name = name;
            this.region = region;
            this.country = country;
        }

        public String getName() { return name; }
        public String getRegion() { return region; }
        public String getCountry() { return country; }

        @Override
        public String toString() {
            return "Location{" +
                    "name='" + name + '\'' +
                    ", region='" + region + '\'' +
                    ", country='" + country + '\'' +
                    '}';
        }
    }

    private Location location;
    private Forecast forecast;

    public WeatherData() {
    }

    public WeatherData(Location location, Forecast forecast) {
        this.location = location;
        this.forecast = forecast;
    }

    public Location getLocation() {
        return location;
    }

    public Forecast getForecast() {
        return forecast;
    }

    @Override
    public String toString() {
        return "WeatherData{" +
                "location=" + location +
                ", forecast=" + forecast +
                '}';
    }
}
