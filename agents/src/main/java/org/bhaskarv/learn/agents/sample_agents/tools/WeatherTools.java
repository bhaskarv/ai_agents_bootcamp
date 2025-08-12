package org.bhaskarv.learn.agents.sample_agents.tools;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.adk.tools.BaseTool;
import com.google.adk.tools.mcp.McpToolset;
import com.google.adk.tools.mcp.SseServerParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@Component
public class WeatherTools {

    private static final Logger log = LoggerFactory.getLogger(WeatherTools.class);
    private static List<BaseTool> toolsList;
    private static List<BaseTool> getTools() {
        SseServerParameters serverParameters = SseServerParameters.builder()
                .url("http://localhost:8080/sse")
                .build();

        McpToolset.McpToolsAndToolsetResult mcpToolsAndToolsetResult;
        try {
            mcpToolsAndToolsetResult = McpToolset.fromServer(serverParameters, new ObjectMapper()).get();
            toolsList = mcpToolsAndToolsetResult.getTools()
                    .stream()
                    .map(mcpTool -> (BaseTool)mcpTool)
                    .toList();
            log.info("*** Discovered tools {}", toolsList);
        } catch (ExecutionException | InterruptedException e) {
            log.error(" Error while getting tools {}", e.getMessage());
            throw new RuntimeException(e);
        }
        return toolsList;
    }

    public static List<BaseTool> tools() {
        if (toolsList == null || toolsList.isEmpty()) {
            toolsList = getTools();
        }
        return toolsList;
    }

    public static BaseTool latLongTool() {
        if (toolsList == null || toolsList.isEmpty()) {
            toolsList = getTools();
        }
        Optional<BaseTool> tool = toolsList.stream()
                .filter(baseTool -> baseTool.name().equals("lat-long-tool"))
                .findFirst();

        return tool.orElse(null);

    }
}

