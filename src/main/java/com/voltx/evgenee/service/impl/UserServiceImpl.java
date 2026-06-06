package com.voltx.evgenee.service.impl;

import com.voltx.evgenee.dto.requests.UserRequestDto;
import com.voltx.evgenee.service.UserService;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {

    @Override
    public String register(UserRequestDto req) {
        return "Hello User welcome to Evgenee";
    }
}
