package com.example.trabalho.controller;

import com.example.trabalho.Model.UserModel;
import com.example.trabalho.repository.UserRepository;
import com.example.trabalho.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserModel user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Email não existe");
        }
        user.setSenha(passwordEncoder.encode(user.getSenha()));
        if (user.getRole() == null){
            user.setRole("USER");
        }
        userRepository.save(user);
        return ResponseEntity.ok("Usuario registrado com sucesso");
    }

    @PostMapping("/login")
    public ResponseEntity<?> authUser(@RequestBody UserModel login) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(login.getEmail(), login.getSenha()));
        String jwt = jwtTokenProvider.generateToken(login.getEmail(),userRepository.findByEmail(login.getEmail()).get().getRole());
        return ResponseEntity.ok(jwt);
    }
}


















