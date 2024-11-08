package com.pecodigos.zapweb.users.controller;

import com.pecodigos.zapweb.security.auth.JwtUtil;
import com.pecodigos.zapweb.users.dtos.UserDTO;
import com.pecodigos.zapweb.users.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Tag(name = "Authentication API", description = "Endpoints for user authentication")
@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    @Operation(summary = "Register user", description = "Creates a new user")
    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@RequestBody UserDTO userDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.register(userDTO));
    }

    @Operation(summary = "Login user", description = "Authenticates a user and returns a token")
    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody UserDTO userDTO) {
        try {
            var loggedInUser = userService.login(userDTO);
            String token = jwtUtil.generateToken(loggedInUser.username());

            Map<String, String> response = new HashMap<>();
            response.put("token", token);
            response.put("userId", loggedInUser.id().toString());

            return ResponseEntity.ok().body(response);
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
