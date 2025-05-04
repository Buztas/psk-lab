package org.psk.lab.user.service;

import org.psk.lab.user.data.dto.UserDTO;
import org.psk.lab.user.data.model.User;
import org.psk.lab.user.data.repository.UserRepository;
import org.psk.lab.user.exception.UserNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DefaultUserService implements UserService {
    private final UserRepository userRepository;

    public DefaultUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getUser(UUID uuid) {
        return userRepository.findById(uuid)
                .orElseThrow(() -> new UserNotFoundException(uuid.toString()));
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public String updateUser(UserDTO user) {
        //TODO: implement this
        return null;
    }

    @Override
    public String deleteUser(UUID uuid) {
        //TODO: implement this
        return "";
    }
}
