package org.psk.lab.user.service;

import org.psk.lab.user.data.dto.UserDTO;
import org.psk.lab.user.data.response.LoginResponse;
import org.psk.lab.user.data.model.MyUser;

public interface AuthService {
    MyUser register(UserDTO userDTO);
    LoginResponse login(String username, String password);
}
