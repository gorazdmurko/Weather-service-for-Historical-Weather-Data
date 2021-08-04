package si.projektna.unit29.adapter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;

public class WeatherApiProxy implements IWeatherApiProxy {

    // private static final Logger log = Logger.getLogger(WeatherApiProxy.class);
    private String url;
    private String apiKey;
    private Boolean writeJson = true;

    // 1. constructor
    public WeatherApiProxy(String url, String apiKey) {
        this.url = url;
        this.apiKey = apiKey;
    }

    // 2. constructor
    public WeatherApiProxy(String url, String apiKey, Boolean writeJson) {
        this.url = url;
        this.apiKey = apiKey;
        this.writeJson = writeJson;
    }

    public WeatherData getHistoricalData(String location, Date date) throws IOException, InterruptedException {     // expected pattern = yyyy-MM-dd

        // 1. client
        HttpClient client = HttpClient.newHttpClient();

        // 2. uri
        StringBuilder uri = new StringBuilder(this.url);
        uri.append("history.json?key=");
        uri.append(URLEncoder.encode(this.apiKey, StandardCharsets.UTF_8));
        uri.append("&q=");
        uri.append(URLEncoder.encode(location, StandardCharsets.UTF_8));
        uri.append("&dt=");
        uri.append(new SimpleDateFormat("yyyy-MM-dd").format(date));

        // 3. request
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri.toString()))
                .header("accept", "application/json")
                .GET()
                .build();

        // 4. response
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if(response.statusCode() != 200) {
            throw new IOException();
        }

        // 5. mapper
        ObjectMapper mapper = new ObjectMapper();
        WeatherData data = mapper.readValue(response.body(), WeatherData.class);
        // WeatherData data = mapper.readValue(response.body(), new TypeReference<WeatherData>() {});

        // 6. json
        if(this.writeJson == true) {
            String path = "WeatherApiAdapter\\WeatherJSON\\";
            path += location + "-hwd" + ".json";
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            mapper.writeValue(new File(path), data);
        }

        return data;
    }
}

