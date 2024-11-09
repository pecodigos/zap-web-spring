package com.pecodigos.zapweb.users.controller;

import com.pecodigos.zapweb.security.auth.JwtUtil;
import com.pecodigos.zapweb.users.dtos.PublicUserListDTO;
import com.pecodigos.zapweb.users.dtos.UserDTO;
import com.pecodigos.zapweb.users.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name = "Users API", description = "Endpoints for user management")
@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController {

    private UserService userService;
    private JwtUtil jwtUtil;

    @Operation(summary = "Get one user", description = "Retrieves a user by their ID")
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable(name = "id") UUID id) {
        return ResponseEntity.ok().body(userService.findById(id));
    }

    @Operation(summary = "Get all users", description = "Retrieves a list of all users")
    @GetMapping("/")
    public ResponseEntity<List<PublicUserListDTO>> list() {
        return ResponseEntity.ok().body(userService.list());
    }

    @Operation(summary = "Update user", description = "Updates an existing user")
    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> update(@PathVariable(name = "id") UUID id, @RequestBody UserDTO userDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.update(id, userDTO));
    }

    @Operation(summary = "Delete user", description = "Deletes a user by their ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable(name = "id") UUID id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

