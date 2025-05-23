package org.attractor.microgram.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.attractor.microgram.dto.UserDto;
import org.attractor.microgram.exception.RegisterException;
import org.attractor.microgram.exception.UserNotFoundException;
import org.attractor.microgram.model.Role;
import org.attractor.microgram.repository.UserRepository;
import org.attractor.microgram.service.RoleService;
import org.attractor.microgram.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.attractor.microgram.model.User;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;
    private final SubscriptionCountService subscriptionService;

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

    @Override
    public List<UserDto> searchUsers(String query) {
        log.info("Searching users with query: {}", query);
        List<User> users = userRepository.findByUsernameContainingIgnoreCaseOrEmailContainingIgnoreCaseOrNameContainingIgnoreCase(query, query, query);
        return users.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    private UserDto mapToDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .name(user.getName())
                .avatar(user.getAvatar())
                .bio(user.getBio())
                .followersCount(subscriptionService.getSubscribersCount(user.getId()))
                .followingCount(subscriptionService.getSubscriptionCount(user.getId()))
                .build();
    }

    @Override
    public UserDto getUserByName(String name) {
        User user = userRepository.findByUsername(name).orElseThrow(() -> new UserNotFoundException("User not found: " + name));

        return mapToDto(user);
    }

    @Override
    public User getUserByNameModel(String name) {
        User user = userRepository.findByUsername(name).orElseThrow(() -> new UserNotFoundException("User not found: " + name));

        return user;
    }

    @Override
    public void saveAvatar(Long userId, String fileName) {
        log.info("Saving avatar for user with id: {} to file: {}", userId, fileName);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found: " + userId));
        user.setAvatar(fileName);
        userRepository.save(user);
        log.info("Avatar saved for user with id: {} to file: {}", userId, fileName);
    }

    @Override
    public void updateUser(UserDto userDto) {
        log.info("Updating user: {}", userDto.getId());
    	User user = userRepository.findById(userDto.getId())
    			.orElseThrow(() -> new UserNotFoundException("User not found: " + userDto.getId()));

    	user.setBio(userDto.getBio());
        userRepository.save(user);
        log.info("User updated: {}", userDto.getId());
    }
}
