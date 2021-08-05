package si.projektna.unit29.gui;

import si.projekna.unit29.wsITF.IWeatherService;
import si.projekna.unit29.wsITF.TempData;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.*;


public class FrameWindow extends JFrame {

    GraphPanel graphPanel;
    BottomPanel panel;

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";


    FrameWindow() {

        this.setTitle("Weather service - historical weather data");
        this.setSize(800, 600);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        panel = new BottomPanel();
        graphPanel = new GraphPanel();
        this.getContentPane().add(BorderLayout.CENTER, graphPanel);
        this.getContentPane().add(BorderLayout.SOUTH, panel);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        run();

    }

    private void run() {
        // 1. action listener
        panel.button.addActionListener(l -> okButtonListener());

        // 2. action listener
        panel.toggle.addActionListener(l -> {
            graphPanel.setCakeGraph();
        });

        // 3. action listener
        panel.refresh.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ArrayList<Float> emptyList = new ArrayList<>();
                // graphPanel.setTemperatures(emptyList.toArray(new Float[0]));
                graphPanel.setTemperatures(null);
                panel.text.setText("");
                panel.button.setEnabled(false);
                panel.toggle.setVisible(false);
            }
        });

        // 4. action listener
        panel.exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        // 5. action listener
        panel.text.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                // System.out.println("Key char: " + e.getKeyChar());
            }

            @Override
            public void keyPressed(KeyEvent e) {
                // System.out.println("Key code: " + e.getKeyCode());
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if(e.getKeyCode() == 10) {
                    okButtonListener();
                }
            }
        });

    }

    private void okButtonListener() {
        clearScreen();
        panel.toggle.setVisible(true); graphPanel.setDefaultCakeGraph();
        int day = 0;
        if(panel.box.getSelectedItem() == panel.getFIRST()) { day = 1; }
        else if(panel.box.getSelectedItem() == panel.getSECOND()) { day = 2; }
        else if(panel.box.getSelectedItem() == panel.getTHIRD()) { day = 3; }
        else if(panel.box.getSelectedItem() == panel.getFOURTH()) { day = 4; }
        else if(panel.box.getSelectedItem() == panel.getFIFTH()) { day = 5; }
        else { System.out.println("wrong item selected"); }

        String city = panel.text.getText().toUpperCase();
        Date xDaysAgo = Date.from( Instant.now().minus(Duration.ofDays(day)) );

        // RMI CLIENT !!!
        try {
            Registry rmi = LocateRegistry.getRegistry(1099);
            IWeatherService server = (IWeatherService) rmi.lookup("WeatherService");

            TempData[] temps = server.getTemperatures(city, xDaysAgo);
            System.out.println(ANSI_PURPLE + "Days ago: " + day + ", City > " + city + ", " + temps[0].getCountry());

            for(TempData td : temps) {
                System.out.println(ANSI_GREEN + "Location: " + td.getLocation() +
                        ANSI_YELLOW + " > time: " + td.getTime() + ANSI_BLUE + " > temp: " + td.getTime() +
                        ANSI_RESET);
            }

            if(city != "") {
                ArrayList<Float> arrayOfTemps = new ArrayList<>();
                ArrayList<Date> arrayOfDates = new ArrayList<>();
                // adding temps & dates from TempData object to a list !!!
                for(TempData td : temps) {
                    arrayOfTemps.add(td.getTemp_c());
                    arrayOfDates.add(td.getTime());
                }
                // changing tempsArray from list into array of floats !!!
                Float[] tempsArray = (Float[]) arrayOfTemps.toArray(new Float[0]);
                Date[] datesArray = (Date[]) arrayOfDates.toArray(new Date[0]);

                graphPanel.setTemperatures(tempsArray);                   // this should set tempsArray of GraphPanel & draw graph ( repaint )
                Float[] temperatures = graphPanel.getTemperatures();        // returns tempsArray from GraphPanel

            }
        } catch (NotBoundException | IOException | InterruptedException remoteException) {
            // TODO Auto-generated catch block
            System.out.println("\nLocation NOT FOUND\n");
            System.out.println(remoteException);
            //remoteException.printStackTrace();
        }
    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

}
