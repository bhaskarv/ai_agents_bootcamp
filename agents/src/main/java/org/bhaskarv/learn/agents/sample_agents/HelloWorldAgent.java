package org.bhaskarv.learn.agents.sample_agents;

import com.google.adk.agents.BaseAgent;
import com.google.adk.agents.LlmAgent;
import com.google.adk.events.Event;
import com.google.adk.runner.InMemoryRunner;
import com.google.adk.sessions.Session;
import com.google.genai.types.Content;
import com.google.genai.types.Part;
import io.reactivex.rxjava3.core.Flowable;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class HelloWorldAgent {
    public static BaseAgent ROOT_AGENT = initAgent();

    private static BaseAgent initAgent() {
        LlmAgent llmAgent = LlmAgent.builder()
                .name("Science app")
                .description("Science teacher agent")
                .model("gemini-2.0-flash")
                .instruction("""
                        You are a helpful science teacher that explains science concepts to kids and teenagers
                        """)
                .outputKey("answer")
                .build();
        return llmAgent;
    }

    public static void main(String[] args) {
        InMemoryRunner runner = new InMemoryRunner(ROOT_AGENT);

        Session session = runner.sessionService()
                .createSession(runner.appName(), "STUDENT")
                .blockingGet();

        try(Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8)) {
            while (true) {
                System.out.println("\nYou > ");
                String userInput = scanner.nextLine();
                if("quit".equalsIgnoreCase(userInput)) {
                    break;
                }

                Content userPrompt  =  Content.fromParts(Part.fromText(userInput));
                Flowable<Event> events = runner.runAsync(session.userId(), session.id(), userPrompt);

                System.out.println("\nAgent > ");
                events.blockingForEach(event -> {
                    System.out.println(event.stringifyContent());
                });

            }
        }
    }
}
