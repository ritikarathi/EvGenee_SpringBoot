package com.voltx.evgenee.service;

import com.voltx.evgenee.dto.requests.LoginRequest;
import com.voltx.evgenee.dto.requests.UserRequestDto;
import com.voltx.evgenee.dto.responses.LoginResponse;
import com.voltx.evgenee.dto.responses.UserResponseDto;
import org.jspecify.annotations.Nullable;

public interface UserService {

    UserResponseDto register(UserRequestDto requestDto);

    LoginResponse login(LoginRequest request);

    UserResponseDto getProfile(String email);

    UserResponseDto updateProfile(String email, UserRequestDto requestDto);
}
