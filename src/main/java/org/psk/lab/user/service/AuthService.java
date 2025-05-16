package org.psk.lab.user.service;

import org.psk.lab.user.data.dto.UserDTO;
import org.psk.lab.user.data.response.LoginResponse;
import org.psk.lab.user.data.model.MyUser;
import org.psk.lab.user.data.response.UserResponse;

public interface AuthService {
    UserResponse register(UserDTO userDTO);
    LoginResponse login(String username, String password);
}
