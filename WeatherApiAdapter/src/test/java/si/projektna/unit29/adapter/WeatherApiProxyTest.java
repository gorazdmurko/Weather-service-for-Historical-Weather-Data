package si.projektna.unit29.adapter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;

import static org.junit.Assert.*;

public class WeatherApiProxyTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testGetHistoricalData() throws IOException, InterruptedException {
        Date xDaysAgo = Date.from( Instant.now().minus(Duration.ofDays(1)) );
        WeatherApiProxy proxy = new WeatherApiProxy("http://api.weatherapi.com/v1/", "3cc0e117d583487d94a84732210807");
        WeatherData wdata = proxy.getHistoricalData("Maribor", xDaysAgo);

        assertNotNull(wdata);
        //assertNotNull(wdata.getForecast().getForecastday()[0].getAstro().getSunrise());
        //assertNotEquals(wdata.getLocation().getName(), "Los Angeles");  // 'Maribor' not equals to 'Los Angeles'

        System.out.println("\nGORAZD MURKO, you did a great job");
        System.out.println(wdata.getLocation().getName());
        System.out.println("Sunrise: " + wdata.getForecast().getForecastday()[0].getAstro().getSunrise());
        System.out.println(wdata.getForecast().getForecastday()[0].getDay().getCondition().getText());
        for(int i = 0; i < 24; i++) {
            if(i < 10) {
                System.out.println("0" + i + ":00 > " + wdata.getForecast().getForecastday()[0].getHour()[i].getTemp_c() + "°C");
            } else {
                System.out.println(i + ":00 > " + wdata.getForecast().getForecastday()[0].getHour()[i].getTemp_c() + "°C");
            }
        }
        System.out.println(wdata.getForecast().getForecastday()[0].getHour().length);
    }
}