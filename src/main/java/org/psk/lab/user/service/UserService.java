package org.psk.lab.user.service;

import org.psk.lab.user.data.dto.UserDTO;
import org.psk.lab.user.data.model.MyUser;

import java.util.List;
import java.util.UUID;

public interface UserService {
    MyUser getUser(UUID uuid);
    List<MyUser> getUsers();
    String updateUser(UserDTO user);
    String deleteUser(UUID uuid);
}
