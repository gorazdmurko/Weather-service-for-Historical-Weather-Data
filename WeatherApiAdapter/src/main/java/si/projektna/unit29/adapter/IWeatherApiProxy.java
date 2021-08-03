package si.projektna.unit29.adapter;

import java.io.IOException;
import java.util.Date;

public interface IWeatherApiProxy {
    public WeatherData getHistoricalData(String location, Date date) throws IOException, InterruptedException;
}
