package si.projektna.unit29.service;

import si.projekna.unit29.wsITF.TempData;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import static org.junit.Assert.*;

public class WeatherServiceTest {

    @org.junit.Before
    public void setUp() throws Exception {
    }

    @org.junit.After
    public void tearDown() throws Exception {
    }

    @org.junit.Test
    public void testGetTemperaturesService() throws IOException, InterruptedException {

        // mocked weather provider ( fake )
        IWeatherProvider provider = new IWeatherProvider() {
            @Override
            public TempData[] getTemperatures(String location, Date date) throws IOException, InterruptedException {
                TempData[] temps = new TempData[4];
                for(int i = 0; i < 4; i++) {
                    Date xDaysAgo = Date.from( Instant.now().minus(Duration.ofDays(i)));
                    temps[i] = new TempData("Maribor", "Slovenija", xDaysAgo, 7.92f - 0.7f*i);
                }
                return temps;
            }
        };

        WeatherService service = new WeatherService(provider);
        Date xDaysAgo = Date.from( Instant.now().minus(Duration.ofDays(5)));
        TempData[] temps = service.getTemperatures("Maribor", xDaysAgo);
        assertEquals(4, temps.length);

        for (TempData td : temps) {
            System.out.println(td);
        }
    }
}