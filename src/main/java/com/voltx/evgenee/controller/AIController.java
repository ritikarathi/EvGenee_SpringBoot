package com.voltx.evgenee.controller;

import com.voltx.evgenee.dto.requests.MessageRequestDto;
import com.voltx.evgenee.dto.responses.MessageResponseDto;
import com.voltx.evgenee.service.AIService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/ai")
@RequiredArgsConstructor
public class AIController {

    private final AIService aiService;

    @PostMapping("/chat")
    public ResponseEntity<MessageResponseDto> chat(
            @RequestBody MessageRequestDto requestDto) {

        return ResponseEntity.ok(
                aiService.chat(requestDto)
        );
    }
}
