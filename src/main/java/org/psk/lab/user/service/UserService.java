package org.psk.lab.user.service;

import org.psk.lab.user.data.dto.UserDTO;
import org.psk.lab.user.data.model.User;

import java.util.List;
import java.util.UUID;

public interface UserService {
    User getUser(UUID uuid);
    List<User> getUsers();
    String updateUser(UserDTO user);
    String deleteUser(UUID uuid);
}
