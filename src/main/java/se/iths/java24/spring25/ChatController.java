package se.iths.java24.spring25;

import org.springframework.ai.chat.messages.*;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class ChatController {

    private final ChatModel chatModel;

    @Autowired
    public ChatController(ChatModel chatModel) {
        this.chatModel = chatModel;
    }

    @GetMapping("/ai/generate")
    public Map<String, String> generate(@RequestParam(value = "message", defaultValue = "Tell me a joke") String message) {

        var message1 = new UserMessage(message);
        var message2 = new SystemMessage("Answer short and concise. Don't start with any info about you being an ai. Start the answer directly.");
        return Map.of("generation", chatModel.call(message1, message2));
    }
}
