package org.psk.lab.user.service;

import jakarta.transaction.Transactional;
import org.psk.lab.mapper.UserMapper;
import org.psk.lab.user.data.dto.UserDTO;
import org.psk.lab.user.data.dto.UserUpdateDTO;
import org.psk.lab.user.data.repository.UserRepository;
import org.psk.lab.user.data.response.UserResponse;
import org.psk.lab.user.exception.UserNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class DefaultUserService implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public DefaultUserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserResponse getUser(UUID id) {
        var user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id.toString()));
        return userMapper.toUserResponse(user);
    }

    @Override
    public List<UserResponse> getUsers() {
        var users = userRepository.findAll();
        return users.stream()
                .map(userMapper::toUserResponse)
                .toList();
    }

    @Override
    @Transactional
    public UserResponse updateUser(UUID id, UserUpdateDTO userDTO) {
        var existingUser = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id.toString()));

        existingUser.setEmail(userDTO.email());

        if (userDTO.roleType() != null) {
            existingUser.setRole(userDTO.roleType());
        }

        var updatedUser = userRepository.save(existingUser);
        return userMapper.toUserResponse(updatedUser);
    }

    @Override
    @Transactional
    public void deleteUser(UUID id) {
        var existingUser = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id.toString()));
        userRepository.delete(existingUser);
    }
}
