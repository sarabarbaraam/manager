package com.sarabarbara.manager.controllers;

import com.sarabarbara.manager.dto.CreateResponse;
import com.sarabarbara.manager.dto.LoginResponse;
import com.sarabarbara.manager.dto.SearchResponse;
import com.sarabarbara.manager.dto.UpdateUserResponse;
import com.sarabarbara.manager.dto.users.UserDTO;
import com.sarabarbara.manager.dto.users.UserLoginDTO;
import com.sarabarbara.manager.dto.users.UserSearchDTO;
import com.sarabarbara.manager.models.Genre;
import com.sarabarbara.manager.models.Users;
import com.sarabarbara.manager.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * UsersControllersTest class
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 18/10/2024
 */

@SpringBootTest
@ActiveProfiles("test")
class UsersControllersTest {

    @Autowired
    private UsersController usersController;

    @Autowired
    private UserRepository userRepository;

    private ResponseEntity<CreateResponse> responseCreate;
    private SearchResponse<UserSearchDTO> responseSearch;
    private Users user1;

    @BeforeEach
    void setUp() {

        userRepository.deleteAll();

        user1 = Users.builder().id(1502L).name("Prueba").username("curcu").password("Testpassword1#")
                .email("test@gmail.com").genre(Genre.PNTS).profilePictureURL(null).premium(true).build();

        responseCreate = usersController.register(user1);
        assertEquals(HttpStatus.CREATED, responseCreate.getStatusCode());
    }

    @Test
    void registerControllerTest() {

        Users userCreated = Users.builder().id(1502L).name("Prueba").username("curcucucucu").password("Testpassword1#")
                .email("test123@gmail.com").genre(Genre.PNTS).profilePictureURL(null).premium(true).build();

        responseCreate = usersController.register(userCreated);
        assertEquals(HttpStatus.CREATED, responseCreate.getStatusCode());
    }

    @Test
    void registerControllerUsernameDuplicatedTest() {

        Users user2 = Users.builder()
                .name("Prueba2")
                .username("curcu")
                .password("Testpassword2#")
                .email("test@gmail.com")
                .genre(Genre.PNTS)
                .profilePictureURL(null)
                .premium(false)
                .build();

        responseCreate = usersController.register(user2);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseCreate.getStatusCode());
    }

    @Test
    void registerControllerEmailDuplicatedTest() {

        Users user2 = Users.builder()
                .name("Prueba2")
                .username("curcu2")
                .password("Testpassword2#")
                .email("test@gmail.com")
                .genre(Genre.PNTS)
                .profilePictureURL(null)
                .premium(false)
                .build();

        responseCreate = usersController.register(user2);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseCreate.getStatusCode());
    }

    @Test
    void searchControllerTotalUserTest() {

        responseSearch = usersController.searchUser("curcu", 0, 10).getBody();

        assertNotNull(responseSearch);
        assertFalse(responseSearch.getResults().isEmpty());

        UserSearchDTO foundUser = responseSearch.getResults().getFirst();

        assertEquals("curcu", foundUser.getUsername());
        assertNull(foundUser.getProfilePictureURL());
    }

    @Test
    void searchControllerPartialUserTest() {

        Users user2 = Users.builder()
                .name("Prueba2")
                .username("curceido")
                .password("Testpassword2#")
                .email("test123@gmail.com")
                .genre(Genre.PNTS)
                .profilePictureURL(null)
                .premium(false)
                .build();
        usersController.register(user2);

        ResponseEntity<SearchResponse<UserSearchDTO>> response = usersController.searchUser("cur", 0, 10);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        responseSearch = response.getBody();

        assertNotNull(responseSearch);
        assertFalse(responseSearch.getResults().isEmpty());

        List<UserSearchDTO> foundUsers = responseSearch.getResults();

        assertEquals(2, foundUsers.size());

        assertTrue(foundUsers.stream().anyMatch(user -> user1.getUsername().equals("curcu")));
        assertTrue(foundUsers.stream().anyMatch(user -> user2.getUsername().equals("curceido")));
    }

    @Test
    void searchControllerZeroUserTest() {

        ResponseEntity<SearchResponse<UserSearchDTO>> response = usersController.searchUser("curct", 0, 10);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void updateUserControllerTest() {

        ResponseEntity<UpdateUserResponse> responseUpdate = usersController.updateUser("curcu",
                UserDTO.builder().username("circulito").build());
        assertEquals(HttpStatus.OK, responseUpdate.getStatusCode());
    }

    @Test
    void updateUserControllerFailTest() {

        ResponseEntity<UpdateUserResponse> responseUpdate = usersController.updateUser("curcu",
                UserDTO.builder().username("curcu").build());
        assertEquals(HttpStatus.BAD_REQUEST, responseUpdate.getStatusCode());
    }

    @Test
    void deleteUserControllerTest() {

        ResponseEntity<String> responseDelete = usersController.deleteUser("curcu");
        assertEquals(HttpStatus.OK, responseDelete.getStatusCode());
    }

    @Test
    void deleteUserControllerFailTest() {

        ResponseEntity<String> responseDelete = usersController.deleteUser("curca");
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseDelete.getStatusCode());
    }

    @Test
    void loginUserControllerTest() {

        UserLoginDTO userLoginDTO =
                UserLoginDTO.builder().username(user1.getUsername()).email(user1.getEmail()).password("Testpassword1" +
                        "#").build();
        ResponseEntity<LoginResponse> responseLogin = usersController.loginUser(userLoginDTO);

        assertEquals(HttpStatus.OK, responseLogin.getStatusCode());
    }

    @Test
    void loginUserControllerFailTest() {

        UserLoginDTO userLoginDTO =
                UserLoginDTO.builder().username(user1.getUsername()).email(user1.getEmail()).password("Testpassword1").build();
        ResponseEntity<LoginResponse> responseLogin = usersController.loginUser(userLoginDTO);

        assertEquals(HttpStatus.UNAUTHORIZED, responseLogin.getStatusCode());
    }

}
