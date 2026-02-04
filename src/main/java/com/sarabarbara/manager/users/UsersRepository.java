package com.sarabarbara.manager.users;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * UsersRepository class.
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 30/12/2025
 */

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {

    Optional<Users> findByUsernameIgnoreCase(String username);
    Optional<Users> findByEmail(String email);

    Page<Users> findByUsernameContainingIgnoreCaseAndActiveTrue(String username, PageRequest pageRequest);
}
