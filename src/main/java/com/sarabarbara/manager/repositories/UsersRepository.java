package com.sarabarbara.manager.repositories;


import com.sarabarbara.manager.entities.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * UsersRepository class.
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 30/12/2025
 */

public interface UsersRepository extends JpaRepository<Users, Long> {

    Optional<Users> findByUsernameIgnoreCase(String username);
    Optional<Users> findByEmail(String email);
    Page<Users> findAllByUsernameContainingIgnoreCase(String username, Pageable pageable);
}
