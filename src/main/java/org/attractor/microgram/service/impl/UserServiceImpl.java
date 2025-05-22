package org.attractor.microgram.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.attractor.microgram.dto.UserDto;
import org.attractor.microgram.exception.RegisterException;
import org.attractor.microgram.model.Role;
import org.attractor.microgram.repository.UserRepository;
import org.attractor.microgram.service.RoleService;
import org.attractor.microgram.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.attractor.microgram.model.User;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    @Transactional
    @Override
    public void registerUser(UserDto userDto) {
        log.info("Registering user: {}", userDto.getEmail());

        if (userRepository.existsByUsername(userDto.getUsername())) {
            throw new RegisterException("User with that name already exists");
        }

        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new RegisterException("User with that email already exists");
        }

        Role userRole = roleService.findByRoleName("USER");

        User user = User.builder()
                .username(userDto.getUsername())
                .email(userDto.getEmail())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .name(userDto.getName())
                .bio(null)
                .roles(List.of(userRole))
                .enabled(true)
                .build();

        userRepository.save(user);


        log.info("User registered: {}", user.getEmail());
    }


}
