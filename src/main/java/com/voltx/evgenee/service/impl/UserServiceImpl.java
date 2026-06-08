package com.voltx.evgenee.service.impl;

import com.voltx.evgenee.dto.requests.LoginRequest;
import com.voltx.evgenee.dto.requests.UserRequestDto;
import com.voltx.evgenee.dto.responses.LoginResponse;
import com.voltx.evgenee.dto.responses.UserResponseDto;
import com.voltx.evgenee.service.UserService;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {

    @Override
    public UserResponseDto register(UserRequestDto req) {
        return new UserResponseDto();
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        return null;
    }

    @Override
    public UserResponseDto getProfile(String email) {
        return null;
    }

    @Override
    public UserResponseDto updateProfile(String email, UserRequestDto requestDto) {
        return null;
    }

    @Override
    public void logout() {

    }

    @Override
    public void forgotPassword(String email) {

    }

    @Override
    public boolean verifyOTP(String email, String otp) {
        return false;
    }

    @Override
    public void resetPassword(String email, String otp, String password) {

    }
}
