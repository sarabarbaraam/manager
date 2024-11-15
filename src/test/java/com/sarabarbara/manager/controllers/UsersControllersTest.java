package com.sarabarbara.manager.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sarabarbara.manager.dto.users.UserDTO;
import com.sarabarbara.manager.dto.users.UserLoginDTO;
import com.sarabarbara.manager.exceptions.UserValidateException;
import com.sarabarbara.manager.models.Genre;
import com.sarabarbara.manager.models.Users;
import com.sarabarbara.manager.services.UsersService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.hamcrest.Matchers.nullValue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * UsersControllersTest class
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 18/10/2024
 */

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class UsersControllersTest {

    @InjectMocks
    private UsersController usersController;

    @Mock
    private UsersService usersService;

    private MockMvc mockMvc;

    private Users user;

    @BeforeEach
    void setUp() {

        mockMvc = MockMvcBuilders.standaloneSetup(usersController).build();

        user = Users.builder()
                .id(1L)
                .name("Prueba")
                .username("curcu")
                .password("Testpassword12#")
                .email("test123@gmail.com")
                .genre(Genre.PNTS)
                .profilePictureURL(null)
                .premium(true)
                .build();
    }

    @Test
    void registerControllerTest() throws Exception {

        when(usersService.createUser(any(Users.class))).thenReturn(user);

        mockMvc.perform(post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Prueba\", \"username\":\"curcu\", \"email\":\"test123@gmail.com\", " +
                                "\"password\":\"Testpassword12#\", \"genre\":\"PNTS\", " +
                                "\"profilePictureURL\":\"null\"," +
                                " \"premium\":true}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("User created successfully"))
                .andExpect(jsonPath("$.userCreate.name").value("Prueba"))
                .andExpect(jsonPath("$.userCreate.username").value("curcu"))
                .andExpect(jsonPath("$.userCreate.email").value("test123@gmail.com"))
                .andExpect(jsonPath("$.userCreate.genre").value("PNTS"))
                .andExpect(jsonPath("$.userCreate.profilePictureURL").value(nullValue()))
                .andExpect(jsonPath("$.userCreate.premium").value(true));
    }

    @Test
    void registerControllerDuplicatedUsernameTest() throws Exception {

        when(usersService.createUser(any(Users.class)))
                .thenThrow(new UserValidateException("Can't create user: The username 'curcu' is already taken."));

        mockMvc.perform(post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Prueba\", \"username\":\"curcu\", \"email\":\"test12@gmail.com\", " +
                                "\"password\":\"Testpassword1#\", \"genre\":\"PNTS\", \"profilePictureURL\":\"null\"," +
                                " \"premium\":true}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message")
                        .value("Can't create user: The username 'curcu' is already taken."));
    }

    @Test
    void registerControllerDuplicatedEmailTest() throws Exception {

        when(usersService.createUser(any(Users.class)))
                .thenThrow(
                        new UserValidateException("Can't create user: The email 'test@gmail.com' is already taken."));

        mockMvc.perform(post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Prueba\", \"username\":\"curcu\", \"email\":\"test@gmail.com\", " +
                                "\"password\":\"Testpassword1#\", \"genre\":\"PNTS\", \"profilePictureURL\":\"null\"," +
                                " \"premium\":true}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message")
                        .value("Can't create user: The email 'test@gmail.com' is already taken."));
    }

    @Test
    void registerControllerErrorTest() throws Exception {

        when(usersService.createUser(any(Users.class))).thenThrow(new RuntimeException("Can't create user: Some " +
                "internal error occurred."));

        mockMvc.perform(post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Prueba\", \"username\":\"curcu\", \"email\":\"test@gmail.com\", " +
                                "\"password\":\"Testpassword1#\", \"genre\":\"PNTS\", \"profilePictureURL\":null," +
                                "\"premium\":true}"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message")
                        .value("Can't create user: Some internal error occurred."));
    }

    @Test
    void searchTotalUserControllerTest() throws Exception {

        when(usersService.searchUser(anyString(), anyInt(), anyInt())).thenReturn(List.of(user));

        mockMvc.perform(post("/search/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("\"curcu\"")
                        .param("page", "1")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.results[0].username").value("curcu"))
                .andExpect(jsonPath("$.results[0].profilePictureURL").value(nullValue()))
                .andExpect(jsonPath("$.totalResults").value(1))
                .andExpect(jsonPath("$.currentPage").value(1))
                .andExpect(jsonPath("$.totalPage").value(1));

    }

    @Test
    void searchPartialUserControllerTest() throws Exception {

        Users user2 = Users.builder()
                .name("Prueba")
                .username("curcoide")
                .password("Testpassword12#")
                .email("test@gmail.com")
                .genre(Genre.PNTS)
                .profilePictureURL(null)
                .premium(true)
                .build();

        when(usersService.searchUser(anyString(), any(Integer.class), any(Integer.class)))
                .thenReturn(List.of(user, user2));

        mockMvc.perform(post("/search/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("\"cur\"")
                        .param("page", "1")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.results[0].username").value("curcu"))
                .andExpect(jsonPath("$.results[0].profilePictureURL").value(nullValue()))
                .andExpect(jsonPath("$.results[1].username").value("curcoide"))
                .andExpect(jsonPath("$.results[1].profilePictureURL").value(nullValue()))
                .andExpect(jsonPath("$.totalResults").value(2))
                .andExpect(jsonPath("$.currentPage").value(1))
                .andExpect(jsonPath("$.totalPage").value(1));
    }

    @Test
    void searchZeroUserControllerTest() throws Exception {

        when(usersService.searchUser(anyString(), anyInt(), anyInt())).thenReturn(List.of());

        mockMvc.perform(post("/search/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("curc")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isNoContent());
    }

    @Test
    void searchUserErrorControllerTest() throws Exception {

        when(usersService.searchUser(anyString(), any(Integer.class), any(Integer.class)))
                .thenThrow(new RuntimeException("Can't search user: Some internal error occurred."));

        mockMvc.perform(post("/search/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("curcu"))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void updateTotalUserControllerTest() throws Exception {

        String identifier = "curcu";

        UserDTO userDTO = UserDTO.builder()
                .name("Test")
                .username("chikitronix")
                .password("TestSuperPAssword123#")
                .email("testemail@gmail.com")
                .genre(Genre.F)
                .profilePictureURL(null)
                .premium(true)
                .build();

        when(usersService.updateUser(anyString(), any(UserDTO.class)))
                .thenReturn(new Users(1L, "Test", "chikitronix",
                        "TestSuperPAssword123#", "testemail@gmail.com",
                        Genre.F, null, true));

        String userUpdateDTOJson = new ObjectMapper().writeValueAsString(userDTO);

        mockMvc.perform(patch("/{identifier}/update", identifier)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userUpdateDTOJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("User updated successfully"))
                .andExpect(jsonPath("$.user.name").value("Test"))
                .andExpect(jsonPath("$.user.username").value("chikitronix"))
                .andExpect(jsonPath("$.user.email").value("testemail@gmail.com"))
                .andExpect(jsonPath("$.user.genre").value("F"))
                .andExpect(jsonPath("$.user.profilePictureURL").isEmpty())
                .andExpect(jsonPath("$.user.premium").value(true))
                .andExpect(jsonPath("$.message").value("User updated successfully"));
    }

    @Test
    void updatePartialUserControllerTest() throws Exception {

        String identifier = "curcu";

        UserDTO userDTO = UserDTO.builder()
                .username("chikitronix")
                .build();

        when(usersService.updateUser(anyString(), any(UserDTO.class)))
                .thenReturn(new Users(1L, "Prueba", "chikitronix",
                        "Testpassword12#", "test123@gmail.com",
                        Genre.PNTS, null, true));

        String userDTOJson = new ObjectMapper().writeValueAsString(userDTO);

        mockMvc.perform(patch("/{identifier}/update", identifier)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userDTOJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("User updated successfully"))
                .andExpect(jsonPath("$.user.username").value("chikitronix"));
    }

    @Test
    void updateUserDuplicatedUsernameControllerTest() throws Exception {

        String identifier = "chikitronix";
        String newIdentifier = "curcu";

        UserDTO userDTO = UserDTO.builder()
                .username(newIdentifier)
                .build();

        when(usersService.updateUser(anyString(), any(UserDTO.class)))
                .thenThrow(new UserValidateException("User update failed. The username " + newIdentifier + " is " +
                        "already taken."));

        String userDTOJson = new ObjectMapper().writeValueAsString(userDTO);

        mockMvc.perform(patch("/{identifier}/update", identifier)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userDTOJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("User update failed. The username " + newIdentifier + " is " +
                        "already taken."));
    }

    @Test
    void updateUserDuplicatedEmailControllerTest() throws Exception {

        String identifier = "curcu";
        String newEmail = "test1234545@gmail.com";

        UserDTO userDTO = UserDTO.builder()
                .email(newEmail)
                .build();

        when(usersService.updateUser(anyString(), any(UserDTO.class)))
                .thenThrow(new UserValidateException("User update failed. The email " + newEmail + " is already taken" +
                        "."));

        String userDTOJson = new ObjectMapper().writeValueAsString(userDTO);

        mockMvc.perform(patch("/{identifier}/update", identifier)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userDTOJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message")
                        .value("User update failed. The email " + newEmail + " is already taken."));
    }

    @Test
    void updateUserInvalidPasswordControllerTest() throws Exception {

        String identifier = "curcu";

        UserDTO userDTO = UserDTO.builder()
                .password("as23")
                .build();

        when(usersService.updateUser(anyString(), any(UserDTO.class)))
                .thenThrow(
                        new UserValidateException("The password does not meet the security requirements. "
                                + "Special characters allowed: !?/@#$%^&*()_+=-"));

        String userDTOJson = new ObjectMapper().writeValueAsString(userDTO);

        mockMvc.perform(patch("/{identifier}/update", identifier)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userDTOJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message")
                        .value("The password does not meet the security requirements. "
                                + "Special characters allowed: !?/@#$%^&*()_+=-"))
                .andExpect(jsonPath("$.user").doesNotExist());
    }

    @Test
    void updateUserControllerErrorTest() throws Exception {

        String identifier = "curcu";

        UserDTO userDTO = UserDTO.builder()
                .username("curcu")
                .build();

        when(usersService.updateUser(anyString(), any(UserDTO.class)))
                .thenThrow(new RuntimeException("Can't update user: Some internal error occurred."));

        String userDTOJson = new ObjectMapper().writeValueAsString(userDTO);

        mockMvc.perform(patch("/{identifier}/update", identifier)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userDTOJson))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message")
                        .value("Can't update user: Some internal error occurred."));

    }

    @Test
    void deleteUserControllerTest() throws Exception {

        String identifier = "curcu";

        mockMvc.perform(delete("/settings/{identifier}", identifier)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("User deleted successfully"));
    }

    @Test
    void deleteUserControllerErrorTest() throws Exception {

        String identifier = "curcu";

        doThrow(new RuntimeException("Can't delete user: Some internal error occurred."));
        mockMvc.perform(delete("/settings/{identifier}", identifier)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Can't delete user: Some internal error occurred."));
    }

    @Test
    void loginUserUsernameControllerTest() throws Exception {

        UserLoginDTO userLoginDTO = UserLoginDTO.builder()
                .username("curcu")
                .password("testPassword123#")
                .build();

        when(usersService.loginUser(any(UserLoginDTO.class))).thenReturn(true);

        String userLoginDTOJson = new ObjectMapper().writeValueAsString(userLoginDTO);

        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userLoginDTOJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Logged successfully"));
    }

    @Test
    void loginUserEmailControllerTest() throws Exception {

        UserLoginDTO userLoginDTO = UserLoginDTO.builder()
                .email("curcu@gmail.com")
                .password("testPassword123#")
                .build();

        when(usersService.loginUser(any(UserLoginDTO.class))).thenReturn(true);

        String userLoginDTOJson = new ObjectMapper().writeValueAsString(userLoginDTO);

        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userLoginDTOJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Logged successfully"));
    }

    @Test
    void loginUserControllerFailedTest() throws Exception {

        UserLoginDTO userLoginDTO = UserLoginDTO.builder()
                .email("curcu@gmail.com")
                .password("testPassword123#")
                .build();

        when(usersService.loginUser(any(UserLoginDTO.class))).thenReturn(false);

        String userLoginDTOJson = new ObjectMapper().writeValueAsString(userLoginDTO);

        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userLoginDTOJson))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message")
                        .value("Can't logged the user. Ensure the email/username and password are correct"));
    }

    @Test
    void loginUserControllerErrorTest() throws Exception {

        UserLoginDTO userLoginDTO = UserLoginDTO.builder()
                .username("curcu")
                .password("testPassword123#")
                .build();

        when(usersService.loginUser(any(UserLoginDTO.class)))
                .thenThrow(new RuntimeException("Can't logged the user: Some internal error occurred."));

        String userLoginDTOJson = new ObjectMapper().writeValueAsString(userLoginDTO);

        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userLoginDTOJson))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message")
                        .value("Can't logged the user: Some internal error occurred."));
    }

}
