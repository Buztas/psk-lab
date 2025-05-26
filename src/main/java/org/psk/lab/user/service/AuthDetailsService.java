package org.psk.lab.user.service;

import org.psk.lab.user.data.repository.UserRepository;
import org.psk.lab.util.interceptor.LogInvocations;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@LogInvocations
public class AuthDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public AuthDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepository.findByEmail(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException(username);
        }
        return User.builder()
                .username(user.get().getEmail())
                .password(user.get().getPassword())
                .roles(user.get().getRole().name())
                .build();
    }
}
