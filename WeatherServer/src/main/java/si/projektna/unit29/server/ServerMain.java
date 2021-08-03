package si.projektna.unit29.server;

import si.projekna.unit29.wsITF.IWeatherService;
import si.projektna.unit29.adapter.IWeatherApiProxy;
import si.projektna.unit29.adapter.WeatherAdapter;
import si.projektna.unit29.adapter.WeatherApiProxy;
import si.projektna.unit29.service.IWeatherProvider;
import si.projektna.unit29.service.WeatherService;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class ServerMain {

    public static void main(String[] args) throws RemoteException {

        IWeatherApiProxy proxy = new WeatherApiProxy("https://api.weatherapi.com/v1/", "3cc0e117d583487d94a84732210807");
        IWeatherProvider provider = new WeatherAdapter(proxy);
        // WeatherService svc = new WeatherService(provider);
        try {

            // IWeatherService server = (IWeatherService) UnicastRemoteObject.exportObject(svc, 0);
            IWeatherService server = new WeatherService(provider);
            Registry rmi = LocateRegistry.createRegistry(1099);
            rmi.bind("WeatherService", server);
            System.out.println("Server started");
            Runtime.getRuntime().addShutdownHook(new Thread() {
                public void run() {
                    System.out.print("Shutting down the server ....");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    try {
                        rmi.unbind("WeatherService");
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    } catch (NotBoundException e) {
                        e.printStackTrace();
                    }
                    System.out.println("\nProcess successfully finished -> server disconnected");
                }
            });
        } catch (RemoteException e) {
            // e.printStackTrace();
            System.out.println("REMOTE EXCEPTION:\n" + e);
        } catch (AlreadyBoundException e) {
            // e.printStackTrace();
            System.out.println("REMOTE EXCEPTION:\n" + e);
        }
    }
}
