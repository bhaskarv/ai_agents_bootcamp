package org.bhaskarv.learn.agents.sample_agents.agent;

import com.google.adk.agents.BaseAgent;
import com.google.adk.agents.LlmAgent;
import org.bhaskarv.learn.agents.sample_agents.tools.WeatherTools;

public class WeatherForecastAgent {
    public static BaseAgent ROOT_AGENT = initAgent();

    private static BaseAgent initAgent() {
        LlmAgent llmAgent = LlmAgent.builder()
                .name("weather_forecast_agent")
                .description("Fetches weather forecast for given latitude and longitude coordinates")
                .model("gemini-2.5-flash")
                .instruction("""
                        You are a helpful agent that fetches weather forecast for a give city based on city name or the given latitude and longitude coordinates.
                        You will use the tools lat-long-tool and forecast-tool for this purpose.
                        The tool forecast_tool gives a JSON response if provided coordinates or the city name are valid.
                        For successful responses you need to generate a JSON containing array with elements DATE, MAX_TEMP, MIN_TEMP, ANALYSIS
                        ANALYSIS element should be summary of the other useful weather data for the day.
                        """)
                .tools(WeatherTools.tools())
                .outputKey("forecast_result")
                .build();
        return llmAgent;
    }
}
