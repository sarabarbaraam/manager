package com.sarabarbara.manager.controllers;

import com.sarabarbara.manager.dto.SearchResponse;
import com.sarabarbara.manager.dto.UpdateUserResponse;
import com.sarabarbara.manager.dto.users.UserDTO;
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

    private ResponseEntity<UserDTO> responseCreate;
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
    void registerTest() {

        assertEquals(HttpStatus.CREATED, responseCreate.getStatusCode());

        UserDTO responseBody = responseCreate.getBody();

        assertNotNull(responseBody);
        assertEquals("Prueba", responseBody.getName());
        assertEquals("curcu", responseBody.getUsername());
        assertEquals("test@gmail.com", responseBody.getEmail());
        assertEquals(Genre.PNTS, responseBody.getGenre());
        assertNull(responseBody.getProfilePictureURL());

        assertNotNull(responseBody.getPremium());
        assertTrue(responseBody.getPremium());
    }

    @Test
    void registerUsernameDuplicatedTest() {

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
    void registerEmailDuplicatedTest() {

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
    void searchTotalUserTest() {

        responseSearch = usersController.searchUser("curcu", 0, 10).getBody();

        assertNotNull(responseSearch);
        assertFalse(responseSearch.getResults().isEmpty());

        UserSearchDTO foundUser = responseSearch.getResults().getFirst();

        assertEquals("curcu", foundUser.getUsername());
        assertNull(foundUser.getProfilePictureURL());
    }

    @Test
    void searchPartialUserTest() {

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

        assertTrue(foundUsers.stream().anyMatch(userSearch1 -> user1.getUsername().equals("curcu")));
        assertTrue(foundUsers.stream().anyMatch(user -> user.getUsername().equals("curceido")));
    }

    @Test
    void searchZeroUserTest() {

        ResponseEntity<SearchResponse<UserSearchDTO>> response = usersController.searchUser("curct", 0, 10);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void updateUserTest() {

        ResponseEntity<UpdateUserResponse> responseUpdate = usersController.updateUser("curcu",
                UserDTO.builder().username("circulito").build());
        assertEquals(HttpStatus.OK, responseUpdate.getStatusCode());
    }

    @Test
    void updateUserFailTest() {

        ResponseEntity<UpdateUserResponse> responseUpdate = usersController.updateUser("curcu",
                UserDTO.builder().username("curcu").build());
        assertEquals(HttpStatus.BAD_REQUEST, responseUpdate.getStatusCode());
    }

    @Test
    void deleteUserTest() {

        ResponseEntity<String> responseDelete = usersController.deleteUser("curcu");
        assertEquals(HttpStatus.OK, responseDelete.getStatusCode());
    }

    @Test
    void deleteUserFailTest() {

        ResponseEntity<String> responseDelete = usersController.deleteUser("curca");
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseDelete.getStatusCode());
    }

}
