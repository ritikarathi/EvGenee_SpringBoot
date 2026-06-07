package com.voltx.evgenee.service;


import com.voltx.evgenee.dto.requests.MessageRequestDto;
import com.voltx.evgenee.dto.responses.MessageResponseDto;

public interface AIService {

    MessageResponseDto chat(MessageRequestDto requestDto);

}