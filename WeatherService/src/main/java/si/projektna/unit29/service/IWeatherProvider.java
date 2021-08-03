package si.projektna.unit29.service;

import si.projekna.unit29.wsITF.TempData;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;

public interface IWeatherProvider {

    public TempData[] getTemperatures(String location, Date date) throws IOException, InterruptedException;
}
