package org.nvision.learn.weather.mcpserver;

import org.nvision.learn.weather.mcpserver.tools.WeatherTools;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class WeatherAdviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(WeatherAdviceApplication.class, args);
	}

	@Bean
	public ToolCallbackProvider weatherServiceTools(WeatherTools weatherTools) {
		return MethodToolCallbackProvider.builder()
				.toolObjects(weatherTools)
				.build();
	}
}
