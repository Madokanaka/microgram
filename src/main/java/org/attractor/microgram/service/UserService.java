package org.attractor.microgram.service;

import org.attractor.microgram.dto.UserDto;
import org.springframework.transaction.annotation.Transactional;

public interface UserService {
    @Transactional
    void registerUser(UserDto userDto);
}
