package com.sarabarbara.manager.repositories;

import com.sarabarbara.manager.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * UserRespository class
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 04/10/2024
 */

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {

    Optional<Users> findByUsername(String username);

    Optional<Users> findByEmail(String email);

}
