package com.pecodigos.zapweb.security.service;

import com.pecodigos.zapweb.users.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var optionalUser = userRepository.findByUsername(username);
        var user = optionalUser.orElseThrow(() -> new UsernameNotFoundException("Username not found."));

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.username())
                .password(user.password())
                .authorities(Collections.emptyList())
                .build();
    }
}
