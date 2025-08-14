package org.bhaskarv.learn.weather.mcpserver.service;

import org.bhaskarv.learn.weather.mcpserver.model.Location;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class LocationService {
    private static final Logger log = LoggerFactory.getLogger(LocationService.class);

    private final RestClient restClient;

    public LocationService(@Value("${location.api.base-url}") String baseUlr) {
        this.restClient = RestClient.builder()
                .baseUrl(baseUlr)
                .defaultHeader("User-Agent", "DemoJavaApp/1.0")
                .build();
    }

    public Location getGeoCoordinates(String city, String country) {
        log.info(" REQUEST RECEIVED FOR CITY {} ", city);
        String response = restClient.get()
                .uri("search?city={city}&country={country}&format=jsonv2&limit=1&addressdetails=1", city, country)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                // Error handling to be added 4xx, 5xx etc
                .body(String.class);

        return getLocation(city, country, response);
    }

    private static Location getLocation(String city, String country, String response) {
        Location location = new Location();
        if (response != null) {
            JSONArray jsonArray = new JSONArray(response);

            if (!jsonArray.isEmpty()) {
                JSONObject jsonObject = jsonArray.getJSONObject(0);
                location.setCityName(city);
                location.setCountry(country);
                double lat = jsonObject.getDouble("lat");
                double lon = jsonObject.getDouble("lon");
                // Rounding lat long value to 4 decimal places - constraint by weather service
                location.setLatitude(round(lat));
                location.setLongitude(round(lon));
            }
        }
        return location;
    }

    private static double round(double value) {
        double scale = Math.pow(10, 4);
        return Math.round(value * scale) / scale;
    }
}
