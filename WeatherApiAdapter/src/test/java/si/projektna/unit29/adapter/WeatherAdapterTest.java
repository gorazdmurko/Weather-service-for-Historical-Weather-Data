package si.projektna.unit29.adapter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import si.projekna.unit29.wsITF.TempData;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;

import static org.junit.Assert.*;

public class WeatherAdapterTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testGetTemperatures() throws IOException, InterruptedException {
        Date xDaysAgo = Date.from( Instant.now().minus(Duration.ofDays(1)) );
        IWeatherApiProxy proxy = new WeatherApiProxy("http://api.weatherapi.com/v1/", "3cc0e117d583487d94a84732210807");
        WeatherData weatherData = proxy.getHistoricalData("Maribor", xDaysAgo);

        int numOfDays = weatherData.getForecast().getForecastday()[0].getHour().length;
        TempData[] tempData = new TempData[numOfDays];      // array of TempData objects !!!!!!!
        for(int i = 0; i < numOfDays; i++) {
            tempData[i] = new TempData(
                    weatherData.getLocation().getName(),
                    weatherData.getLocation().getCountry(),
                    weatherData.getForecast().getForecastday()[0].getHour()[i].getTime(),
                    weatherData.getForecast().getForecastday()[0].getHour()[i].getTemp_c()
            );
        }

        assertNotNull(tempData);
        for(int i = 0; i < numOfDays; i++) {
            if(i < 10) {
                System.out.println("0" + i + ": " + tempData[i]);
            } else {
                System.out.println(i + ": " + tempData[i]);
            }
        }
    }
}