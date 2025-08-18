package org.bhaskarv.learn.agents.weather_forecast.controller;

import com.google.adk.events.Event;
import com.google.adk.runner.InMemoryRunner;
import com.google.adk.sessions.Session;
import com.google.genai.types.Content;
import com.google.genai.types.Part;
import io.reactivex.rxjava3.core.Flowable;
import org.bhaskarv.learn.agents.sample_agents.HelloWorldAgent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AgentController {

    private static final Logger log = LoggerFactory.getLogger(AgentController.class);

    @GetMapping("/agent/chat")
    public String chat(@RequestParam(name="prompt") String userPrompt) {
        InMemoryRunner runner = new InMemoryRunner(HelloWorldAgent.ROOT_AGENT);

        Session session = runner.sessionService()
                .createSession(runner.appName(), "STUDENT")
                .blockingGet();

        Content prompt  =  Content.fromParts(Part.fromText(userPrompt));
        Flowable<Event> events = runner.runAsync(session.userId(), session.id(), prompt);

        StringBuilder response = new StringBuilder();
        events.blockingForEach(event -> {
            String content = event.stringifyContent();
            log.info(" AGENT RESPONSE {}", content);
            response.append(content);
        });
        return  response.toString();
    }
}
