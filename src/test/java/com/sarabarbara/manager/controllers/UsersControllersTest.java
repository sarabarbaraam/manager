package com.sarabarbara.manager.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sarabarbara.manager.dto.CreateResponse;
import com.sarabarbara.manager.dto.LoginResponse;
import com.sarabarbara.manager.dto.SearchResponse;
import com.sarabarbara.manager.dto.UpdateUserResponse;
import com.sarabarbara.manager.dto.users.*;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
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
    private UserCreateDTO userCreateDTO;

    @BeforeEach
    void setUp() {

        mockMvc = MockMvcBuilders.standaloneSetup(usersController).build();

        userCreateDTO =
                UserCreateDTO.builder()
                        .name("Prueba")
                        .username("curco")
                        .email("test123@gmail.com")
                        .genre(Genre.PNTS)
                        .profilePictureURL(null)
                        .premium(true)
                        .build();
    }

    @Test
    void registerControllerTest() throws Exception {

        CreateResponse response = CreateResponse.builder()
                .success(true)
                .user(userCreateDTO)
                .message("User created successfully")
                .build();

        when(usersService.createUser(any(Users.class))).thenReturn(response);

        mockMvc.perform(post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Prueba\", \"username\":\"curcu\", \"email\":\"test123@gmail.com\", " +
                                "\"password\":\"Testpassword1#\", \"genre\":\"PNTS\", \"profilePictureURL\":\"null\"," +
                                " \"premium\":true}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("User created successfully"));
    }

    @Test
    void registerControllerDuplicatedUsernameTest() throws Exception {

        CreateResponse response = CreateResponse.builder()
                .success(false).user(userCreateDTO)
                .message("Can't create user: The username 'curcu' is already taken.")
                .build();
        when(usersService.createUser(any(Users.class))).thenReturn(response);

        mockMvc.perform(post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Prueba\", \"username\":\"curcu\", \"email\":\"test12@gmail.com\", " +
                                "\"password\":\"Testpassword1#\", \"genre\":\"PNTS\", \"profilePictureURL\":\"null\"," +
                                " \"premium\":true}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Can't create user: The username 'curcu' is already taken."));
    }

    @Test
    void registerControllerDuplicatedEmailTest() throws Exception {

        CreateResponse response = CreateResponse.builder()
                .success(false).user(userCreateDTO)
                .message("Can't create user: The email 'test@gmail.com' is already taken.").build();

        when(usersService.createUser(any(Users.class))).thenReturn(response);

        mockMvc.perform(post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Prueba\", \"username\":\"curcu\", \"email\":\"test@gmail.com\", " +
                                "\"password\":\"Testpassword1#\", \"genre\":\"PNTS\", \"profilePictureURL\":\"null\"," +
                                " \"premium\":true}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Can't create user: The email 'test@gmail.com' is already " +
                        "taken."));
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
                .andExpect(jsonPath("$.message").value("Can't create user: Some internal error occurred."));
    }

    @Test
    void searchTotalUserControllerTest() throws Exception {

        UserSearchDTO searchDTO = UserSearchDTO.builder()
                .username("curcu")
                .profilePictureURL(null)
                .build();
        SearchResponse<UserSearchDTO> response =
                SearchResponse.<UserSearchDTO>builder()
                        .results(Collections.singletonList(searchDTO))
                        .totalResults(1)
                        .currentPage(0)
                        .totalPage(1)
                        .build();

        when(usersService.searchUser(anyString(), any(Integer.class), any(Integer.class))).thenReturn(response);

        mockMvc.perform(post("/search/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("curcu")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.results[0].username").value("curcu"))
                .andExpect(jsonPath("$.totalResults").value(1));

    }

    @Test
    void searchPartialUserControllerTest() throws Exception {

        List<UserSearchDTO> searchDTOList = new ArrayList<>(Arrays.asList(
                UserSearchDTO.builder()
                        .username("curcu")
                        .build(),
                UserSearchDTO.builder()
                        .username("curcolopodis")
                        .build()
        ));

        SearchResponse<UserSearchDTO> response =
                SearchResponse.<UserSearchDTO>builder()
                        .results(searchDTOList)
                        .totalResults(2)
                        .currentPage(0)
                        .totalPage(1)
                        .build();

        when(usersService.searchUser(anyString(), any(Integer.class), any(Integer.class))).thenReturn(response);

        mockMvc.perform(post("/search/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("curc")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.results[0].username").value("curcu"))
                .andExpect(jsonPath("$.results[1].username").value("curcolopodis"))
                .andExpect(jsonPath("$.totalResults").value(2));
    }

    @Test
    void searchZeroUserControllerTest() throws Exception {

        SearchResponse<UserSearchDTO> response =
                SearchResponse.<UserSearchDTO>builder()
                        .results(Collections.emptyList())
                        .totalResults(0)
                        .currentPage(0)
                        .totalPage(1)
                        .build();

        when(usersService.searchUser(anyString(), any(Integer.class), any(Integer.class))).thenReturn(response);

        mockMvc.perform(post("/search/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("curc")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isNoContent());
    }

    @Test
    void searchUserErrorControllerTest() throws Exception {

        when(usersService.searchUser(anyString(), any(Integer.class), any(Integer.class))).thenThrow(new RuntimeException("Can't search user: Some internal error occurred."));

        mockMvc.perform(post("/search/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("curcu"))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void updateTotalUserControllerTest() throws Exception {

        String identifier = "curcu";

        UserDTO userDTO = UserDTO.builder()
                .id(1233L)
                .name("Curcu")
                .username("curcu")
                .password("newPassword123#")
                .email("test123@gmail.com")
                .genre(Genre.PNTS)
                .profilePictureURL(null)
                .premium(false)
                .build();

        UpdateUserResponse response = UpdateUserResponse.builder()
                .success(true)
                .message("User updated successfully")
                .user(UserUpdateDTO.builder()
                        .name("Test")
                        .username("tasty")
                        .email("test12@gmail.com")
                        .genre(Genre.F)
                        .profilePictureURL(null)
                        .premium(true)
                        .build())
                .build();

        when(usersService.updateUser(anyString(), any(UserDTO.class))).thenReturn(response);

        String userDTOJson = new ObjectMapper().writeValueAsString(userDTO);

        mockMvc.perform(patch("/{identifier}/update", identifier)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userDTOJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("User updated successfully"))
                .andExpect(jsonPath("$.user.name").value("Test"))
                .andExpect(jsonPath("$.user.username").value("tasty"))
                .andExpect(jsonPath("$.user.email").value("test12@gmail.com"))
                .andExpect(jsonPath("$.user.genre").value("F"))
                .andExpect(jsonPath("$.user.profilePictureURL").isEmpty())
                .andExpect(jsonPath("$.user.premium").value(true))
                .andExpect(jsonPath("$.message").value("User updated successfully"));
    }

    @Test
    void updatePartialUserControllerTest() throws Exception {

        String identifier = "curcu";

        UserDTO userDTO = UserDTO.builder()
                .username("curcu")
                .build();

        UpdateUserResponse response = UpdateUserResponse.builder()
                .success(true)
                .message("User updated successfully")
                .user(UserUpdateDTO.builder()
                        .username("tasty")
                        .build())
                .build();

        when(usersService.updateUser(anyString(), any(UserDTO.class))).thenReturn(response);

        String userDTOJson = new ObjectMapper().writeValueAsString(userDTO);

        mockMvc.perform(patch("/{identifier}/update", identifier)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userDTOJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("User updated successfully"))
                .andExpect(jsonPath("$.user.username").value("tasty"));
    }

    @Test
    void updateUserBadRequestControllerTest() throws Exception {

        String identifier = "curcu";

        UserDTO userDTO = UserDTO.builder()
                .username("curcu")
                .build();

        UpdateUserResponse response = UpdateUserResponse.builder()
                .success(false)
                .message("User update failed")
                .user(UserUpdateDTO.builder()
                        .username("curcu")
                        .build())
                .build();

        when(usersService.updateUser(anyString(), any(UserDTO.class))).thenReturn(response);

        String userDTOJson = new ObjectMapper().writeValueAsString(userDTO);

        mockMvc.perform(patch("/{identifier}/update", identifier)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userDTOJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("User update failed"))
                .andExpect(jsonPath("$.user.username").value("curcu"));
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
                .andExpect(jsonPath("$.message").value("Can't update user: Some internal error occurred."));

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
    void loginUserControllerTest() throws Exception {

        UserLoginDTO userLoginDTO = UserLoginDTO.builder()
                .username("curcu")
                .password("testPassword123#")
                .build();

        LoginResponse response = LoginResponse.builder()
                .success(true)
                .message("Logged successfully")
                .build();

        when(usersService.loginUser(any(UserLoginDTO.class))).thenReturn(response);

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
                .username("curcu")
                .password("testPassword123#")
                .build();

        LoginResponse response = LoginResponse.builder()
                .success(false)
                .message("Can't logged the user. Ensure the email/username and password are correct")
                .build();

        when(usersService.loginUser(any(UserLoginDTO.class))).thenReturn(response);

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
                .andExpect(jsonPath("$.message").value("Can't logged the user: Some internal error occurred."));
    }

}
