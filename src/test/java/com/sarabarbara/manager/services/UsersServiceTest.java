package com.sarabarbara.manager.services;

import com.sarabarbara.manager.dto.LoginResponse;
import com.sarabarbara.manager.dto.SearchResponse;
import com.sarabarbara.manager.dto.users.UserDTO;
import com.sarabarbara.manager.dto.users.UserLoginDTO;
import com.sarabarbara.manager.dto.users.UserSearchDTO;
import com.sarabarbara.manager.exceptions.UserNotFoundException;
import com.sarabarbara.manager.exceptions.UserValidateException;
import com.sarabarbara.manager.models.Genre;
import com.sarabarbara.manager.models.Users;
import com.sarabarbara.manager.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("test")
class UsersServiceTest {

    @Autowired
    private UsersService usersService;

    @Autowired
    private UserRepository userRepository;

    private Users user;

    @BeforeEach
    void init() {

        userRepository.deleteAll();

        user = Users.builder().id(1502L).name("Prueba").username("prueba").password("Testpassword1#")
                .email("test@gmail.com").genre(Genre.PNTS).profilePictureURL(null).premium(true).build();

        usersService.createUser(user);
    }

    @Test
    void createUserTest() {

        Users createdUser = Users.builder().name("Pruebas").username("pruebasS123").password("Testpassword#12")
                .email("test123@gmail.com").genre(Genre.F).profilePictureURL(null).premium(false).build();

        usersService.createUser(createdUser);
        assertThat(userRepository.findByUsernameIgnoreCase(createdUser.getUsername())).isPresent();
    }

    @Test
    void createUserErrorTest() {

        Users badUser = Users.builder().name("Pruebas").username("pruebasS").password("Testpassword12")
                .email("test23@gmail.com").genre(Genre.F).profilePictureURL(null).premium(false).build();

        assertThrows(UserValidateException.class, () -> usersService.createUser(badUser));
    }

    @Test
    void searchTotalUserTest() {

        SearchResponse<UserSearchDTO> response = usersService.searchUser("pru", 0, 10);

        assertThat(response.getTotalResults()).isEqualTo(1);
        assertThat(response.getResults()).hasSize(1);

        UserSearchDTO foundUser = response.getResults().getFirst();
        assertThat(foundUser.getUsername()).isEqualTo("prueba");
        assertThat(foundUser.getProfilePictureURL()).isNull();
    }

    @Test
    void searchPartialUserTest() {

        Users user2 = Users.builder().name("Pruebas").username("pruebasS").password("Testpassword1#2")
                .email("test23@gmail.com").genre(Genre.F).profilePictureURL(null).premium(false).build();

        userRepository.save(user2);

        SearchResponse<UserSearchDTO> response = usersService.searchUser("a", 0, 10);

        assertThat(response.getTotalResults()).isEqualTo(2);
        assertThat(response.getResults()).hasSize(2);
    }

    @Test
    void searchZeroUserTest() {

        SearchResponse<UserSearchDTO> response = usersService.searchUser("NonExistent", 0, 10);

        assertThat(response.getTotalResults()).isZero();
        assertThat(response.getResults()).isEmpty();
    }

    @Test
    void updateTotalUserTest() {

        UserDTO updateUser =
                UserDTO.builder().name("Pruebas").username("pruebasS").password("Testpassword1#2")
                        .email("test23@gmail.com").genre(Genre.F).profilePictureURL(null).premium(true).build();

        usersService.updateUser(user.getUsername(), updateUser);

        assertThat(updateUser.getName()).isEqualTo("Pruebas");
        assertThat(updateUser.getUsername()).isEqualTo("pruebasS");
        assertThat(updateUser.getPassword()).isEqualTo("Testpassword1#2");
        assertThat(updateUser.getEmail()).isEqualTo("test23@gmail.com");
        assertThat(updateUser.getGenre()).isEqualTo(Genre.F);
        assertThat(updateUser.getProfilePictureURL()).isNull();

    }

    @Test
    void updatePartialUserTest() {

        UserDTO updateUser = UserDTO.builder().username("pruebasS").build();

        usersService.updateUser(user.getUsername(), updateUser);

        assertThat(updateUser.getUsername()).isEqualTo("pruebasS");
    }

    @Test
    void updateUserErrorTest() {

        UserDTO updateUser = UserDTO.builder().name("Pruebas").username("pruebasS").password("Testpassword1#2")
                .email("test@gmail.com").genre(Genre.F).profilePictureURL(null).premium(false).build();

        String identifier = user.getUsername();

        assertThrows(UserValidateException.class, () -> usersService.updateUser(identifier, updateUser));
    }

    @Test
    void deleteUserTest() {


        usersService.deleteUser(user.getUsername());

        assertThat(userRepository.findByUsernameIgnoreCase(user.getUsername())).isEmpty();

    }

    @Test
    void deleteUserErrorTest() {

        UserDTO userToDelete = UserDTO.builder().id(1804L).name("Pruebas").username("pruebasSfdds5").password(
                        "Testpassword1#2")
                .email("test23@gmail.com").genre(Genre.F).profilePictureURL(null).premium(false).build();

        String username = userToDelete.getUsername();
        assertThrows(UserNotFoundException.class, () -> usersService.deleteUser(username));
    }

    @Test
    void loginUserUsernameTest() {

        UserLoginDTO loginUser =
                UserLoginDTO.builder().username(user.getUsername()).password("Testpassword1#").build();

        LoginResponse loginResponse = usersService.loginUser(loginUser);

        assertThat(loginResponse.isSuccess()).isTrue();
        assertThat(loginResponse.getMessage()).isEqualTo("Logged successfully");
    }

    @Test
    void loginUserEmailTest() {

        UserLoginDTO loginUser =
                UserLoginDTO.builder().email(user.getEmail()).password("Testpassword1#").build();

        LoginResponse loginResponse = usersService.loginUser(loginUser);

        assertThat(loginResponse.isSuccess()).isTrue();
        assertThat(loginResponse.getMessage()).isEqualTo("Logged successfully");
    }

    @Test
    void loginUserErrorTest() {

        UserLoginDTO loginUser =
                UserLoginDTO.builder().email(user.getEmail()).password("Testpassword1").build();

        LoginResponse loginResponse = usersService.loginUser(loginUser);

        assertThat(loginResponse.isSuccess()).isFalse();
        assertThat(loginResponse.getMessage()).isEqualTo("Can't logged the user. Ensure the email/username and " +
                "password are correct");
    }

}

