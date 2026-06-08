package com.voltx.evgenee.service;

import com.voltx.evgenee.dto.responses.AiChatResponse;

public interface AIService {
    AiChatResponse processVoiceChat(String message, String threadId, String userEmail, Double latitude, Double longitude);
}