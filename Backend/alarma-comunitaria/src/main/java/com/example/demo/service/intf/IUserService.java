package com.example.demo.service.intf;

import com.example.demo.model.User;
import org.springframework.stereotype.Service;

@Service
public interface IUserService {
    User findByUsername(String username);
}
