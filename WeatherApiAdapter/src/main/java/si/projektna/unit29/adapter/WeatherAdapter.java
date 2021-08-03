package si.projektna.unit29.adapter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import si.projekna.unit29.wsITF.TempData;
import si.projektna.unit29.service.IWeatherProvider;

import java.io.File;
import java.io.IOException;
import java.util.Date;

public class WeatherAdapter implements IWeatherProvider {

    // private static final Logger log = Logger.getLogger(WeatherAdapter.class);
    private IWeatherApiProxy proxy;
    private Boolean writeJson = true;

    // 1. constructor
    public WeatherAdapter(IWeatherApiProxy proxy) {
        this.proxy = proxy;
    }

    // 2. constructor
    public WeatherAdapter(IWeatherApiProxy proxy, Boolean writeJson) {
        this.proxy = proxy;
        this.writeJson = writeJson;
    }

    @Override
    public TempData[] getTemperatures(String location, Date date) throws IOException, InterruptedException {

        WeatherData weatherData = proxy.getHistoricalData(location, date);      // object !!!!

        int numOfDays = weatherData.getForecast().getForecastday()[0].getHour().length;

        TempData[] tempData = new TempData[numOfDays];      // array of TempData objects !!!!

        for(int i = 0; i < numOfDays; i++) {
            tempData[i] = new TempData(
                    weatherData.getLocation().getName(),
                    weatherData.getLocation().getCountry(),
                    weatherData.getForecast().getForecastday()[0].getHour()[i].getTime(),
                    weatherData.getForecast().getForecastday()[0].getHour()[i].getTemp_c()
            );
        };

        // PROBE
        if(this.writeJson == true) {
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            String path = "Data\\";
            path += weatherData.getLocation().getName() + ".json";
            mapper.writeValue(new File(path), tempData);
        }

        return tempData;
    }
}

