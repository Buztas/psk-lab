package org.psk.lab.user.service;

import org.psk.lab.user.data.dto.UserDTO;
import org.psk.lab.user.data.model.MyUser;
import org.psk.lab.user.data.response.UserResponse;

import java.util.List;
import java.util.UUID;

public interface UserService {
    UserResponse getUser(UUID uuid);
    List<UserResponse> getUsers();
    UserResponse updateUser(UUID id, UserDTO user);
    void deleteUser(UUID uuid);
}
