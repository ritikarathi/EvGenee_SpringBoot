package com.voltx.evgenee.service;


import com.voltx.evgenee.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public String register(String obj) {
      //  String status = userRepository.save(obj);
        return "Hello";
    }
}
