package org.psk.lab.user.service;

import io.jsonwebtoken.InvalidClaimException;
import org.psk.lab.mapper.UserMapper;
import org.psk.lab.user.data.dto.UserDTO;
import org.psk.lab.user.data.response.LoginResponse;
import org.psk.lab.user.data.model.MyUser;
import org.psk.lab.user.data.repository.UserRepository;
import org.psk.lab.user.exception.InvalidUserCredentials;
import org.psk.lab.user.exception.UserAlreadyExistsException;
import org.psk.lab.user.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class DefaultAuthService implements AuthService {
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final Logger LOGGER = LoggerFactory.getLogger(DefaultAuthService.class);

    public DefaultAuthService(UserMapper userMapper, UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public MyUser register(UserDTO userDTO) {
        try {
            var existingUser = userRepository.findByEmail(userDTO.email());
            if(existingUser.isEmpty()) {
                var encryptedUserDTO = new UserDTO(
                        userDTO.email(),
                        passwordEncoder.encode(userDTO.password()),
                        userDTO.roleType(),
                        userDTO.version()
                );
                var user = userMapper.toEntity(encryptedUserDTO);
                LOGGER.info("User registered successfully: {}", user);
                return userRepository.save(user);
            }
            LOGGER.info("User already exists: {}", userDTO.email());
            throw new UserAlreadyExistsException(userDTO.email());
        } catch (Exception e) {
            throw new RuntimeException("Error creating user", e);
        }
    }

    @Override
    public LoginResponse login(String username, String password) {
        try {
            var myUser = userRepository.findByEmail(username)
                    .filter(user -> passwordEncoder.matches(password, user.getPassword()))
                    .orElseThrow(() -> new InvalidUserCredentials("Invalid username or password"));

            String token = jwtUtil.generateToken(myUser);
            var userResponse = userMapper.toUserResponse(myUser);
            LOGGER.info("User logged in successfully: {}", userResponse);
            return new LoginResponse(token, userResponse);
        } catch (InvalidUserCredentials e) {
            LOGGER.error("Error logging in, invalid username or password");
            throw e;
        }
    }
}
