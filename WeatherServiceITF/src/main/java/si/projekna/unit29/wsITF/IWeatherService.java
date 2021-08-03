package si.projekna.unit29.wsITF;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.HashMap;

public interface IWeatherService extends Remote {
    TempData[] getTemperatures(String location, Date date) throws IOException, InterruptedException;      // IOException, InterruptedException,
    // TEST FUNCTION 1
    String logOut() throws RemoteException;
}
