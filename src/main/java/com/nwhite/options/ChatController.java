package com.nwhite.options;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ChatController {
    private static final Logger log = LoggerFactory.getLogger(ChatController.class);
    private final ChatClient chatClient;

    public ChatController(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    @GetMapping("/")
    public ChatResponse chat() {
        return chatClient.prompt()
                .user("Tell me an interesting fact about GPT-5")
                .options(OpenAiChatOptions.builder().model("minimax-m2").temperature(1.0))
                .call()
                .chatResponse();
    }

    @GetMapping("/creative")
    public String creativeWriting() {
        return chatClient.prompt()
                .user("Write a short creative and funny story about job interview.")
                .options(OpenAiChatOptions.builder()
                        .model("minimax-m2")
                        .temperature(1.0)
                        .presencePenalty(0.6)
                        .maxTokens(1500))
                .call()
                .content();
    }

    @GetMapping("/facts")
    public String factualExample() {
        return chatClient.prompt()
                .user("What is the capital of France? Provide a factual answer.")
                .options(OpenAiChatOptions.builder()
                        .model("minimax-m2")
                        .temperature(0.1)
                        .frequencyPenalty(0.0)
                        .maxTokens(500))
                .call()
                .content();
    }

    @GetMapping("/code")
    public String codeGen() {
        return chatClient.prompt()
                .user("Write a Java method to calculate factorial. Start with ```java and end with ```")
                .options(OpenAiChatOptions.builder()
                        .model("minimax-m2")
                        .temperature(0.3)
                        .maxTokens(1500)
                        .stop(List.of("END_CODE", "\n\n---")))
                .call()
                .content();
    }
}
