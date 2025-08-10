package org.nvision.learn.weather.mcpserver.controller;

import org.nvision.learn.weather.mcpserver.model.Location;
import org.nvision.learn.weather.mcpserver.service.LocationService;
import org.nvision.learn.weather.mcpserver.service.WeatherForecastService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/geo")
public class WeatherController {

    private final LocationService locationService;
    private final WeatherForecastService weatherForecastService;

    public WeatherController(LocationService locationService, WeatherForecastService weatherForecastService) {
        this.locationService = locationService;
        this.weatherForecastService = weatherForecastService;
    }

    @GetMapping("/coordinates")
    public String getCoordinates(@RequestParam(name="country") String country, @RequestParam(name="city") String city) {
        return locationService.getGepCoordinates(city, country).toString();
    }

    @GetMapping("/forecast")
    public String forecast(@RequestParam(name="country") String country, @RequestParam(name="city") String city) {
        Location location = locationService.getGepCoordinates(city, country);
        return  weatherForecastService.forecastForGeoLocation(location.getLatitude(), location.getLongitude());
    }
}
