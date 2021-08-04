package si.projektna.unit29.rest.RestService;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import si.projekna.unit29.wsITF.IWeatherService;
import si.projekna.unit29.wsITF.TempData;
import si.projektna.unit29.adapter.IWeatherApiProxy;
import si.projektna.unit29.adapter.WeatherAdapter;
import si.projektna.unit29.adapter.WeatherApiProxy;
import si.projektna.unit29.service.IWeatherProvider;
import si.projektna.unit29.service.WeatherService;

import javax.imageio.ImageIO;
import javax.inject.Singleton;
import javax.swing.plaf.ColorUIResource;
import javax.ws.rs.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;

@Path("/weatherResource")
@Singleton
public class WeatherResource {


    @Path("/{date}")
    @GET
    @Produces("application/json")
    public TemperatureGraph getTemperatureGraph(@PathParam("date") String dateString, @QueryParam("location") String location,
                                                @QueryParam("width") int width, @QueryParam("height") int height) {
        Date date = null;
        try {
            date = new SimpleDateFormat("dd.MM.yyyy").parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        IWeatherApiProxy proxy = new WeatherApiProxy("https://api.weatherapi.com/v1/", "3cc0e117d583487d94a84732210807", false);
        IWeatherProvider provider = new WeatherAdapter(proxy, false);
        IWeatherService service = new WeatherService(provider);

        TempData[] temps = null;
        try {
            temps = service.getTemperatures(location, date);        // -> then he call .getTemperatures on provider, then .getHistoricalData on proxy
        } catch (IOException | InterruptedException e) {
            // e.printStackTrace();
            System.out.println("Error occurred:\nFailed to provide requested data");
            System.out.println(e);
        }

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
        Graphics2D g2D = image.createGraphics();
        g2D.setColor(Color.BLACK);
        g2D.fillRect(0, 0, width, height);

        int xPanel = width;       // width / height of panel
        int yPanel = height;
        int xStart = 30;
        int yStart = yPanel - yPanel/4;     // starting X & Y points of coordinate system
        int xEnd = xPanel - 20;
        int yEnd = 20;                      // ending X & Y points of coordinate system
        int xsize = xEnd - xStart;          // X size of co.sys.
        int ysize = yStart - yEnd;          // y size of co.sys.

        int dx = (xsize) / 24;  // dx
        int dy = (ysize) / 8;  // dy

        // draw grid
        g2D.setColor(Color.GREEN.darker());   // color of co.sys. x,y
        g2D.drawLine(xStart, yStart, xStart, yEnd);  // y-line of co.sys.
        g2D.drawLine(xStart, yStart, xEnd, yStart);  // x-line of co.sys

        // x - line
        for (int i = 0; i < 24; i++) {
            int x = xStart + dx*i + dx/2;
            g2D.drawLine(x, yStart + 5, x, yStart - 5);
            String hoursStr = Integer.toString(i + 1);
            g2D.drawString(hoursStr, x - dx / 10, yStart + 20);
        }
        // y - line
        for (int i = 1; i <= 8; i++) {
            int y = yStart - dy*i;
            g2D.drawLine(xStart - 5, y, xStart + 5, y);
            String tempStr = Integer.toString(i * 5);
            g2D.drawString(tempStr, xStart - 25, y + dy / 10);
        }

        if(temps != null) {
            for (int i = 0; i < temps.length && i < 24; i++) {
                float temp = temps[i].getTemp_c();
                int x = xStart + dx*i +1;
                if (temp > 0) {
                    int h = (int) (dy*temp)/5 - 1;
                    // g2D.setColor(Color.magenta);
                    g2D.setColor(new ColorUIResource(25, 109, 187));
                    g2D.fillRect(x, yStart - h, dx - 5, h);
                } else if (temp < 0) {
                    int h = (int) (dy*temp)/5 + 1;     // height < 0 -> negative
                    // g2D.setColor(Color.blue);
                    g2D.setColor(new ColorUIResource(181, 247, 225));
                    g2D.fillRect(x, yStart + 1, dx - 5, - h);

                    g2D.setColor(Color.yellow);
                    String hours = Integer.toString(i + 1);
                    g2D.drawString(hours, x + dx/3, yStart - 10);
                }
            }
            //
            int elements = temps.length;
            // float[] xPoints = new float[elements];
            float[] yPoints = new float[elements];
            int[] xPts = new int[elements];     // ure
            int[] yPts = new int[elements];     // temperature

            // iz 'ArrayList' dam temperature v 'array' !!
            for (int i = 0; i < elements; i++) {
                yPoints[i] = temps[i].getTemp_c();
            }

            // temperature pretvorim iz float v int !!
            for (int i = 0; i < elements; i++) {
                yPts[i] = (int) yPoints[i];
                // xPts[i] = (int) xPoints[i];
            }

            for (int i = 0; i < elements; i++) {
                int x = xStart + dx * i + dx / 2;
                xPts[i] = x;

                int temp = yPts[i];
                height = temp * dy / 5;

                yPts[i] = yStart - height;
            }

            g2D.setColor(Color.magenta);
            g2D.setStroke(new BasicStroke(3));
            g2D.drawPolyline(xPts, yPts, elements);
        } else {
            g2D.setColor(Color.red);
            g2D.drawLine(0, 0, width, height);
            g2D.drawLine(width, 0, 0, height);
            g2D.setColor(new Color(213,231, 123));
            g2D.setFont(new Font("Arial", Font.BOLD, 20));
            g2D.drawString("TEMPERATURES > NULL", image.getWidth() * 2 / 5, image.getHeight() / 2);
        }

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, "png", stream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String imageBytes = Base64.getEncoder().encodeToString(stream.toByteArray());
        TemperatureGraph tempGraph = new TemperatureGraph(location, date, width, height, imageBytes);

        return tempGraph;
    }

    // T - E - S - T -I - N - G
    TempData[] tempsTest = new TempData[5];                     // array of objects
    HashMap<String, TempData> hashTest = new HashMap<>();       // map ( object w/ key - value pairs )

    // 1. TEST
    @GET
    @Produces("text/plain")
    public String hello() {
        return "Hello, World!";
    }

    // 2. TEST
    @Path("/tempsTest")
    @GET
    @Produces("application/json")
    public TempData[] testGetTempData() {
        for(int i = 0; i < 5; i++) {
            tempsTest[i] = new TempData("Maribor", "Slovenia", new Date(), 11.3f + i);
        }
        return tempsTest;
    }

    // 3. TEST
    @Path("/hashTest")
    @GET
    @Produces("application/json")
    public HashMap<String, TempData> testGetHashData() {
        for(TempData temp : tempsTest) {
            hashTest.put(temp.getLocation(), temp);
        }
        return hashTest;
    }

    // 4. TEST
    @Path("/parseJson")      // http://localhost:8081/RestService/api/weatherResource/parseJson
    @GET
    @Produces("application/json")
    public JsonNode testParseJson() {
        // 1. OPTION -> from file to object (node)
        ObjectMapper mapper = new ObjectMapper();
        String path = "C:\\Users\\Gorazd Murko\\Desktop\\Unit29_PROJEKTNA\\WeatherApiAdapter\\WeatherJSON\\berlin-hwd.json";
        JsonNode node = null;
        try {
            node = mapper.readValue(new File(path), JsonNode.class);
        } catch (IOException e) {
            System.out.println(e);
            // e.printStackTrace();
        }

        // 2. OPTION -> from string to object
        // String string = "{\"name\": \"Sam Smith\", \"technology\": \"Python\"}";
        // JSONObject json = new JSONObject(string);
        // return json;

        return node;
    }

    // 5. TEST
    @Path("/stringifyJson")     // http://localhost:8081/RestService/api/weatherResource/stringifyJson
    @GET
    @Produces("text/plain")
    public String testStringifyJson() {
        ObjectMapper mapper = new ObjectMapper();
        String path = "C:\\Users\\Gorazd Murko\\Desktop\\Unit29_PROJEKTNA\\WeatherApiAdapter\\WeatherJSON\\berlin-hwd.json";
        JsonNode src = null;
        try {
            src = mapper.readValue(new File(path), JsonNode.class);
        } catch (IOException e) {
            System.out.println(e);
            // e.printStackTrace();
        }

        StringWriter writer = new StringWriter();
        JsonFactory factory = new JsonFactory();
        try {
            JsonGenerator generator = factory.createGenerator(writer);
            mapper.writeTree(generator, src);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return writer.toString();
    }

}