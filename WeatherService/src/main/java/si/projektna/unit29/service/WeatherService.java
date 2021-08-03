package si.projektna.unit29.service;

import si.projekna.unit29.wsITF.IWeatherService;
import si.projekna.unit29.wsITF.TempData;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Date;
import java.util.HashMap;

public class WeatherService implements IWeatherService {

    IWeatherProvider provider;

    public WeatherService(IWeatherProvider provider) {
        try {
            UnicastRemoteObject.exportObject(this, 0);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        this.provider = provider;
    }


    @Override
    public TempData[] getTemperatures(String location, Date date) throws IOException, InterruptedException {

        TempData[] temps = provider.getTemperatures(location, date);

        return temps;       // returns array ob (TempData) objects ( { time, temp }, { time, temp } ... );
    }

    // TEST FUNCTION 1
    @Override
    public String logOut() {
        return "\nTEST FUNCTION\nIt's working!";
    }

}

