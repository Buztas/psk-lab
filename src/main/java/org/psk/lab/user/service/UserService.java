package org.psk.lab.user.service;

import org.psk.lab.user.data.dto.UserUpdateDTO;
import org.psk.lab.user.data.response.UserResponse;

import java.util.List;
import java.util.UUID;

public interface UserService {
    UserResponse getUser(UUID uuid);
    List<UserResponse> getUsers();
    UserResponse updateUser(UUID id, UserUpdateDTO userDTO);
    void deleteUser(UUID uuid);
}
