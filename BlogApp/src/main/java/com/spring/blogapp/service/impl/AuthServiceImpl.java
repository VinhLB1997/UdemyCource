package com.spring.blogapp.service.impl;

import com.spring.blogapp.entity.Role;
import com.spring.blogapp.entity.User;
import com.spring.blogapp.exception.BlogAPIException;
import com.spring.blogapp.exception.DataNotFoundException;
import com.spring.blogapp.model.LoginDTO;
import com.spring.blogapp.model.RegisterDTO;
import com.spring.blogapp.repository.RoleRepository;
import com.spring.blogapp.repository.UserRepository;
import com.spring.blogapp.service.AuthService;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthServiceImpl(AuthenticationManager authenticationManager, UserRepository userRepository,
                           PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @Override
    public String login(LoginDTO accountLogin) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(accountLogin.getUsername(),accountLogin.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return "Login Success";
    }

    @Override
    public String register(RegisterDTO accountRegister) {
        if(userRepository.existsByUsername(accountRegister.getUsername())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "User is already!");
        }
        if(userRepository.existsByEmail(accountRegister.getEmail())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Email is already!");
        }

        User user  = new User();
        user.setName(accountRegister.getName());
        user.setEmail(accountRegister.getEmail());
        user.setUsername(accountRegister.getUsername());
        user.setPassword(passwordEncoder.encode(accountRegister.getPassword()));
        Set<Role> roles = new HashSet<>();
        Role role = roleRepository.findByName("ROLE_USER").orElseThrow(() -> new DataNotFoundException("Role","",""));
        roles.add(role);
        user.setRoles(roles);
        userRepository.save(user);
        return "Register Success";
    }
}
