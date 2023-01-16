package com.spring.blogapp.service;

import com.spring.blogapp.model.LoginDTO;
import com.spring.blogapp.model.RegisterDTO;

public interface AuthService {

    String login(LoginDTO accountLogin);

    String register(RegisterDTO accountRegister);
}
