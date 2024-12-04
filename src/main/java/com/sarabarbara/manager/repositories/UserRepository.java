package com.sarabarbara.manager.repositories;

import com.sarabarbara.manager.models.users.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * UserRepository class
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 04/10/2024
 */

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {

    Optional<Users> findByUsernameIgnoreCase(@Param("username") String username);

    Optional<Users> findByEmail(@Param("email") String email);

    Page<Users> findAllByUsernameContainingIgnoreCase(@Param("username") String username, Pageable pageable);

}
