package org.bhaskarv.learn.weather.mcpserver.tools;

import org.bhaskarv.learn.weather.mcpserver.model.Location;
import org.bhaskarv.learn.weather.mcpserver.service.LocationService;
import org.bhaskarv.learn.weather.mcpserver.service.WeatherForecastService;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

@Service
public class WeatherTools {

    LocationService locationService;
    WeatherForecastService forecastService;

    public WeatherTools(WeatherForecastService forecastService, LocationService locationService) {
        this.forecastService = forecastService;
        this.locationService = locationService;
    }

    @Tool(name = "lat-long-tool", description = "Gets latitude and longitude values for a given city & country combination")
    public Location locationTool(String countryName, String cityName) {
        return locationService.getGeoCoordinates(cityName, countryName);
    }

    @Tool(name = "forecast-tool", description = "Fetches weather forecast for a given lat-long comibnation")
    public String forecast(double latitude, double longitude) {
        return forecastService.forecastForGeoLocation(latitude, longitude);
    }
}
