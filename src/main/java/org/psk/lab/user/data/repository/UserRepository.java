package org.psk.lab.user.data.repository;

import org.psk.lab.user.data.model.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<MyUser, UUID> {
    Optional<MyUser> findByEmail(String email);
}
