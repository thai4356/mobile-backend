package mobibe.mobilebe.controller;

import lombok.RequiredArgsConstructor;
import mobibe.mobilebe.dto.request.ai.AIChatReq;
import mobibe.mobilebe.dto.response.ai.AIChatRes;
import mobibe.mobilebe.other_service.ai.AIService;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/ai")
public class AIController {

    private final AIService aiService;

    public AIController(AIService aiService) {
        this.aiService = aiService;
    }

    @PostMapping("/chat")
    public AIChatRes chat(@RequestBody AIChatReq req) {
        return aiService.chat(req.getMessage());
    }
}
