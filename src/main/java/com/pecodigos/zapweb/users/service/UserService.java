package com.pecodigos.zapweb.users.service;

import com.pecodigos.zapweb.exceptions.UserAlreadyExistsException;
import com.pecodigos.zapweb.users.dtos.UserDTO;
import com.pecodigos.zapweb.enums.Role;
import com.pecodigos.zapweb.users.dtos.mapper.UserMapper;
import com.pecodigos.zapweb.users.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public UserDTO findById(UUID id) {
        return userRepository.findById(id)
                .map(userMapper::toDto)
                .orElseThrow(() -> new NoSuchElementException("No user with that name."));
    }

    public List<UserDTO> list() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toDto)
                .toList();
    }

    public UserDTO login(UserDTO userDTO) {
        var optionalUser = userRepository.findByUsername(userDTO.username());

        if (optionalUser.isEmpty()) {
            throw new BadCredentialsException("Invalid username or password.");
        }

        var user = optionalUser.get();

        if (!passwordEncoder.matches(userDTO.password(), user.password())) {
            throw new BadCredentialsException("Invalid username or password.");
        }

        return user;
    }

    public UserDTO register(UserDTO userDTO) {
        if (userRepository.findByUsername(userDTO.username()).isPresent()) {
            throw new UserAlreadyExistsException("User already exists with that username.");
        }

        if (userRepository.findByEmail(userDTO.email()).isPresent()) {
            throw new UserAlreadyExistsException("User already exists with that e-mail.");
        }

        var user = userMapper.toEntity(userDTO);
        user.setPassword(passwordEncoder.encode(userDTO.password()));

        if (user.getRole() == null) {
            user.setRole(Role.MEMBER);
        }

        return userMapper.toDto(userRepository.save(user));
    }

    public UserDTO update(UUID id, UserDTO userDTO) {
        if (userRepository.findByUsername(userDTO.username()).isPresent()) {
            throw new UserAlreadyExistsException("User already exists with that username.");
        }

        if (userRepository.findByEmail(userDTO.email()).isPresent()) {
            throw new UserAlreadyExistsException("User already exists with that e-mail.");
        }

        return userRepository.findById(id)
                .map(data -> {
                    data.setName(userDTO.name());
                    data.setUsername(userDTO.username());
                    data.setEmail(userDTO.email());
                    data.setPassword(passwordEncoder.encode(userDTO.password()));

                    return userMapper.toDto(userRepository.save(data));
                }).orElseThrow(() -> new NoSuchElementException("No user found with that ID."));
    }

    public void delete(UUID id) {
        userRepository.deleteById(id);
    }
}
