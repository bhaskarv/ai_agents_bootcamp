package org.nvision.learn.weather.mcpserver.service;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class WeatherForecastService {

    private static final Logger log = LoggerFactory.getLogger(WeatherForecastService.class);
    private final RestClient restClient;

    private final String baseUrl;

    public WeatherForecastService(@Value("${forecast.api.base-url}") String baseUrl) {
        this.baseUrl = baseUrl;
        this.restClient = RestClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader("User-Agent","demo-weather-app/1.0")
               // .defaultHeader("Accept", "application/geo+json")
                .build();
    }

    public String forecastForGeoLocation(double latitude, double longitude) {
        log.info("INSIDE FORECAST {}, {}", latitude, longitude);

        String response = restClient.get()
                .uri("points/{lat},{lon}", latitude, longitude)
                .retrieve() //Error handling to be added
                .body(String.class);

        log.info(" FORECAST METHOD {},{},{}", latitude, longitude, response);

        String forecastData = "NO DATA";
        if (response != null) {
            JSONObject rootObject = new JSONObject(response);
            String forecastApiUrl = rootObject.getJSONObject("properties").getString("forecast");

            log.info("FORECAST API URL {}", forecastApiUrl);

            if(!forecastApiUrl.startsWith(baseUrl)) {
                log.error("ERROR WHILE RETRIEVING FORECAST URL");
                throw new RuntimeException(" Error while retrieving forecast url");
            }
            forecastData = getForecastData(forecastApiUrl);
        }

        return forecastData;
    }

    private String getForecastData(String url) {
        RestClient restClient1  = RestClient.builder().baseUrl(url).build();
        return restClient1.get().retrieve().body(String.class);
    }
}
