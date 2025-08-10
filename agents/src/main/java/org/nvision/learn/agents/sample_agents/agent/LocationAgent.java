package org.nvision.learn.agents.sample_agents.agent;

import com.google.adk.agents.BaseAgent;
import com.google.adk.agents.LlmAgent;
import org.nvision.learn.agents.sample_agents.tools.WeatherTools;

public class LocationAgent {
    public static BaseAgent ROOT_AGENT = initAgent();

    private static BaseAgent initAgent() {
        LlmAgent llmAgent = LlmAgent.builder()
                .name("Geo_location_agent")
                .description("Fetches Geographic latitude and longitude coordinates for a given city and country combination")
                .model("gemini-2.5-flash")
                .instruction("""
                        You are a helpful agent that fetches latitude and longitude coordinates for a give
                        city and country combination. You will use a tool lat-long-tool for this purpose
                        """)
                .tools(WeatherTools.latLongTool())
                .outputKey("geo_coordinates")
                .build();
        return llmAgent;
    }
}
