package com.example.trabalho.controller;

import com.example.trabalho.Model.UserModel;
import com.example.trabalho.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/user/profile")
    public ResponseEntity<?> getUserProfile() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<UserModel> user = userRepository.findByEmail(email);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/user/profile")
    public ResponseEntity<?> updateUserProfile(@RequestBody UserModel updatedUser) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<UserModel> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            UserModel user = userOptional.get();
            user.setNome(updatedUser.getNome());
            if (updatedUser.getSenha() != null && !updatedUser.getSenha().isEmpty()) {
                user.setSenha(passwordEncoder.encode(updatedUser.getSenha()));
            }
            userRepository.save(user);
            return ResponseEntity.ok("Mudado com sucesso");
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/admin/users")
    public ResponseEntity<List<UserModel>> getAllUsers() {
        return ResponseEntity.ok(userRepository.findAll());
    }

    @PutMapping("/admin/users/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody UserModel updatedUser) {
        Optional<UserModel> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            UserModel user = userOptional.get();
            user.setNome(updatedUser.getNome());
            user.setEmail(updatedUser.getEmail());
            user.setRole(updatedUser.getRole());
            if (updatedUser.getSenha() != null && !updatedUser.getSenha().isEmpty()) {
                user.setSenha(passwordEncoder.encode(updatedUser.getSenha()));
            }
            userRepository.save(user);
            return ResponseEntity.ok("mudado com sucesso");
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/admin/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return ResponseEntity.ok("Usuario deletado com sucesso");
        }
        return ResponseEntity.notFound().build();
    }

}
