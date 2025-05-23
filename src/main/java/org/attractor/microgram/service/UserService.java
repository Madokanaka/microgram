package org.attractor.microgram.service;

import org.attractor.microgram.dto.UserDto;
import org.attractor.microgram.model.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserService {
    @Transactional
    void registerUser(UserDto userDto);

    List<UserDto> searchUsers(String query);

    UserDto getUserByName(String name);

    User getUserByNameModel(String name);

    void saveAvatar(Long userId, String fileName);

    void updateUser(UserDto userDto);
}
