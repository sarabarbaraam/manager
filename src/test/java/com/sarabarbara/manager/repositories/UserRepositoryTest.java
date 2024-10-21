package com.sarabarbara.manager.repositories;

import com.sarabarbara.manager.models.Genre;
import com.sarabarbara.manager.models.Users;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * UserRespositoryTest class
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 16/10/2024
 */

@SpringBootTest
@ActiveProfiles("test")
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void init() {

        userRepository.deleteAll();

        Users user = Users.builder().id(1502L).name("Prueba").username("prueba").password("Testpassword1#")
                .email("test@gmail.com").genre(Genre.PNTS).profilePictureURL(null).premium(true).build();
        userRepository.save(user);

        Users user2 = Users.builder().id(1502L).name("Prueba").username("pruebas").password("Testpassword1#")
                .email("test123@gmail.com").genre(Genre.PNTS).profilePictureURL(null).premium(true).build();

        userRepository.save(user2);
    }

    @Test
    void findByUsernameIgnoreCaseTest() {

        Optional<Users> foundUser = userRepository.findByUsernameIgnoreCase("prueba");

        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getUsername()).isEqualTo("prueba");
    }

    @Test
    void findByEmailTest() {

        Optional<Users> foundUser = userRepository.findByEmail("test@gmail.com");

        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getEmail()).isEqualTo("test@gmail.com");
    }

    @Test
    void findAllByUsernameContainingTest() {

        Pageable pageable = PageRequest.of(0, 10);

        Page<Users> foundUser = userRepository.findAllByUsernameContaining("prueba", pageable);

        assertThat(foundUser).isNotEmpty().allMatch(user -> user.getUsername().contains("prueba"));
    }

}
