package com.sarabarbara.manager.services;

import com.sarabarbara.manager.apis.ZeroBounceAPI;
import com.sarabarbara.manager.dto.users.UserDTO;
import com.sarabarbara.manager.dto.users.UserLoginDTO;
import com.sarabarbara.manager.enums.user.UserGenre;
import com.sarabarbara.manager.exceptions.users.UserNotFoundException;
import com.sarabarbara.manager.exceptions.users.UserValidateException;
import com.sarabarbara.manager.models.users.Users;
import com.sarabarbara.manager.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class UsersServiceTest {

    @InjectMocks
    private UsersService usersService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ZeroBounceAPI zeroBounceAPI;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    private Users user;
    private final PageRequest pageRequest = PageRequest.of(0, 10);

    @BeforeEach
    void setUp() {

        user = Users.builder()
                .id(1L)
                .name("Curcu")
                .username("curcu")
                .email("email@gmail.com")
                .password("Testpassword124#!")
                .userGenre(UserGenre.PNTS)
                .profilePictureURL(null)
                .premium(true).build();

    }

    /**
     * The createUser Service
     */

    @Test
    void createUserServiceTest() {

        when(userRepository.save(any(Users.class))).thenReturn(user);

        Users createdUser = usersService.createUser(user);

        assertThat(createdUser.getId()).isEqualTo(1L);
        assertThat(createdUser.getName()).isEqualTo("Curcu");
        assertThat(createdUser.getUsername()).isEqualTo("curcu");
        assertThat(createdUser.getEmail()).isEqualTo("email@gmail.com");
        assertThat(createdUser.getPassword()).startsWith("$2a$10");
        assertThat(createdUser.getUserGenre()).isEqualTo(UserGenre.PNTS);
        assertNull(createdUser.getProfilePictureURL());
        assertTrue(createdUser.getPremium());
    }

    @Test
    void CreateUserServiceDuplicatedUsernameTest() throws UserValidateException {

        when(userRepository.findByUsernameIgnoreCase(anyString()))
                .thenReturn(Optional.of(user));

        UserValidateException exception = assertThrows(UserValidateException.class,
                () -> usersService.createUser(user));

        assertThat(exception.getMessage())
                .isEqualTo("The username " + user.getUsername() + " is already taken.");

    }

    @Test
    void CreateUserServiceDuplicatedEmailTest() throws UserValidateException {

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));

        UserValidateException exception = assertThrows(UserValidateException.class,
                () -> usersService.createUser(user));

        assertThat(exception.getMessage()).isEqualTo("The email " + user.getEmail() + " is already taken.");
    }

    @Test
    void createUserServiceInvalidPasswordTest() throws UserValidateException {

        Users user2 = Users.builder()
                .id(5332L)
                .name("Test")
                .username("carapan")
                .email("email@gmail.com")
                .password("Testpassword124")
                .userGenre(UserGenre.PNTS)
                .profilePictureURL(null)
                .premium(true).build();

        UserValidateException exception = assertThrows(UserValidateException.class,
                () -> usersService.createUser(user2));
        assertThat(exception.getMessage())
                .isEqualTo("The password does not meet the security requirements. "
                        + "Special characters allowed: !?/@#$%^&*()_+=-");
    }

    @Test
    void searchTotalUserServiceTest() {

        List<Users> userList = new ArrayList<>(Collections.singletonList(user));

        Page<Users> pageList = new PageImpl<>(userList, pageRequest, userList.size());

        when(userRepository.findAllByUsernameContainingIgnoreCase("curcu", pageRequest)).thenReturn(pageList);

        List<Users> usersList = usersService.searchUser("curcu", 0, 10);

        assertThat(usersList).hasSize(1);
        assertThat(userList.get(0).getUsername()).isEqualTo("curcu");

    }

    /**
     * The searchUser Service
     */

    @Test
    void searchPartialUserServiceTest() {

        Users user3 = Users.builder()
                .id(5332L)
                .name("Test")
                .username("curcaido")
                .email("email@gmail.com")
                .password("Testpassword124")
                .userGenre(UserGenre.PNTS)
                .profilePictureURL(null)
                .premium(true).build();

        List<Users> userList = new ArrayList<>(List.of(user, user3));

        Page<Users> pageList = new PageImpl<>(userList, pageRequest, userList.size());

        when(userRepository.findAllByUsernameContainingIgnoreCase("cur", pageRequest)).thenReturn(pageList);

        List<Users> usersList = usersService.searchUser("cur", 0, 10);

        assertThat(usersList).hasSize(2);
        assertThat(userList.get(0).getUsername()).isEqualTo("curcu");
        assertThat(userList.get(1).getUsername()).isEqualTo("curcaido");
    }

    @Test
    void searchZeroUserServiceTest() {

        Page<Users> emptyPage = new PageImpl<>(Collections.emptyList(), pageRequest, 0);

        when(userRepository.findAllByUsernameContainingIgnoreCase("chikitronix", pageRequest)).thenReturn(emptyPage);

        List<Users> usersList = usersService.searchUser("chikitronix", 0, 10);

        assertThat(usersList).isEmpty();
    }

    /**
     * The updateUser Service
     */

    @Test
    void updateTotalUserServiceTest() {

        when(userRepository.findByUsernameIgnoreCase("curcu")).thenReturn(Optional.of(user));
        when(userRepository.findByUsernameIgnoreCase("curiquiqui")).thenReturn(Optional.empty());

        UserDTO newInfo = UserDTO.builder()
                .name("Test")
                .username("curiquiqui")
                .email("email@gmail.com")
                .password("Testpassword124$")
                .userGenre(UserGenre.F)
                .profilePictureURL(null)
                .premium(false)
                .build();

        Users updateUser = usersService.updateUser(user.getUsername(), newInfo);

        assertThat(updateUser.getName()).isEqualTo("Test");
        assertThat(updateUser.getUsername()).isEqualTo("curiquiqui");
        assertThat(updateUser.getEmail()).isEqualTo("email@gmail.com");
        assertThat(updateUser.getPassword()).startsWith("$2a$10");
        assertThat(updateUser.getUserGenre()).isEqualTo(UserGenre.F);
        assertNull(updateUser.getProfilePictureURL());
        assertFalse(updateUser.getPremium());
    }

    @Test
    void updatePartialUserServiceTest() {

        when(userRepository.findByUsernameIgnoreCase("curcu")).thenReturn(Optional.of(user));
        when(userRepository.findByUsernameIgnoreCase("curiquiqui")).thenReturn(Optional.empty());

        UserDTO newInfo = UserDTO.builder()
                .name("Test")
                .username("curiquiqui")
                .build();

        Users updateUser = usersService.updateUser(user.getUsername(), newInfo);

        assertThat(updateUser.getName()).isEqualTo("Test");
        assertThat(updateUser.getUsername()).isEqualTo("curiquiqui");
    }

    @Test
    void updateUsernameFailServiceTest() throws UserValidateException {

        Users user4 = Users.builder()
                .id(14L)
                .name("Tralala")
                .username("carapan")
                .email("emailtest@gmail.com")
                .password("Testpassword124#!")
                .userGenre(UserGenre.PNTS)
                .profilePictureURL(null)
                .premium(true)
                .build();

        when(userRepository.findByUsernameIgnoreCase("curcu")).thenReturn(Optional.of(user));
        when(userRepository.findByUsernameIgnoreCase("carapan")).thenReturn(Optional.of(user4));

        UserDTO newInfo = UserDTO.builder()
                .username("carapan")
                .build();

        UserValidateException exception = assertThrows(UserValidateException.class,
                () -> usersService.updateUser("curcu", newInfo));

        assertThat(exception.getMessage())
                .isEqualTo("Username validation failed: The username "
                        + user4.getUsername() + " is already taken.");
    }

    @Test
    void updateEmailFailServiceTest() throws UserValidateException {

        Users user5 = Users.builder()
                .id(14L)
                .name("Tralala")
                .username("carapan")
                .email("emailtest@gmail.com")
                .password("Testpassword124#!")
                .userGenre(UserGenre.PNTS)
                .profilePictureURL(null)
                .premium(true)
                .build();

        when(userRepository.findByUsernameIgnoreCase("curcu")).thenReturn(Optional.of(user));
        when(userRepository.findByEmail("emailtest@gmail.com")).thenReturn(Optional.of(user5));

        UserDTO newInfo = UserDTO.builder()
                .email("emailtest@gmail.com")
                .build();

        UserValidateException exception = assertThrows(UserValidateException.class,
                () -> usersService.updateUser("curcu", newInfo));

        assertThat(exception.getMessage())
                .isEqualTo("Email validation failed: The email "
                        + user5.getEmail() + " is already taken.");
    }

    @Test
    void updatePasswordFailServiceTest() throws UserValidateException {

        when(userRepository.findByUsernameIgnoreCase(anyString())).thenReturn(Optional.of(user));

        UserDTO newInfo = UserDTO.builder()
                .password("cuchurrumin")
                .build();

        UserValidateException exception = assertThrows(UserValidateException.class,
                () -> usersService.updateUser("curcu", newInfo));

        assertThat(exception.getMessage())
                .isEqualTo("Password validation failed: The password does not meet the security requirements. "
                        + "Special characters allowed: !?/@#$%^&*()_+=-");

    }

    @Test
    void updateEqualPasswordFailServiceTest() {

        passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode("Testpassword124#!");

        Users user9 = Users.builder()
                .id(1L)
                .name("Curcu")
                .username("curcu")
                .email("email@gmail.com")
                .password(encodedPassword)
                .userGenre(UserGenre.PNTS)
                .profilePictureURL(null)
                .premium(true).build();

        when(userRepository.findByUsernameIgnoreCase(anyString())).thenReturn(Optional.of(user9));

        UserDTO newInfo = UserDTO.builder()
                .password("Testpassword124#!")
                .build();

        UserValidateException exception = assertThrows(UserValidateException.class,
                () -> usersService.updateUser("curcu", newInfo));

        assertThat(exception.getMessage())
                .isEqualTo("Password validation failed: The password can't be the same as the previous one");

    }

    @Test
    void updateUserFailServiceTest() throws UserNotFoundException {

        when(userRepository.findByUsernameIgnoreCase(anyString())).thenReturn(Optional.empty());

        UserDTO newInfo = UserDTO.builder()
                .email("emailtest@gmail.com")
                .build();

        UserNotFoundException exception = assertThrows(UserNotFoundException.class,
                () -> usersService.updateUser("chikitronix", newInfo));

        assertThat(exception.getMessage()).isEqualTo("Can't update user: User not found");
    }

    /**
     * The deleteUser Service
     */

    @Test
    void deleteUserServiceTest() {

        when(userRepository.findByUsernameIgnoreCase(anyString())).thenReturn(Optional.of(user));

        usersService.deleteUser(user.getUsername());

        verify(userRepository, times(1)).deleteById(user.getId());
    }

    @Test
    void deleteUserFailServiceTest() throws UserNotFoundException {

        when(userRepository.findByUsernameIgnoreCase(anyString())).thenReturn(Optional.empty());

        UserNotFoundException exception = assertThrows(UserNotFoundException.class,
                () -> usersService.deleteUser("curcu"));

        verify(userRepository, never()).deleteById(anyLong());
        assertThat(exception.getMessage()).isEqualTo("User not found.");
    }

    /**
     * The loginUser Service
     */

    @Test
    void loginUserUsernameServiceTest() {

        passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode("Testpassword124#!");

        Users user5 = Users.builder()
                .id(1L)
                .name("Curcu")
                .username("carapan")
                .email("email@gmail.com")
                .password(encodedPassword)
                .userGenre(UserGenre.PNTS)
                .profilePictureURL(null)
                .premium(true).build();

        UserLoginDTO userLoginDTO = UserLoginDTO.builder()
                .username("curcu")
                .password("Testpassword124#!")
                .build();

        when(userRepository.findByUsernameIgnoreCase(anyString())).thenReturn(Optional.of(user5));

        boolean loggedUser = usersService.loginUser(userLoginDTO);

        assertTrue(loggedUser);
    }

    @Test
    void loginUserEmailServiceTest() {

        passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode("Testpassword124#!");

        Users user6 = Users.builder()
                .id(1L)
                .name("Curcu")
                .username("carapan")
                .email("email@gmail.com")
                .password(encodedPassword)
                .userGenre(UserGenre.NB)
                .profilePictureURL(null)
                .premium(true).build();

        UserLoginDTO userLoginDTO = UserLoginDTO.builder()
                .email("email@gmail.com")
                .password("Testpassword124#!")
                .build();

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user6));

        boolean loggedUser = usersService.loginUser(userLoginDTO);

        assertTrue(loggedUser);
    }

    @Test
    void loginUserFailWithUsernameServiceTest() {

        passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode("Testpassword124#!");

        Users user7 = Users.builder()
                .id(1L)
                .name("Curcu")
                .username("carapan")
                .email("email@gmail.com")
                .password(encodedPassword)
                .userGenre(UserGenre.M)
                .profilePictureURL(null)
                .premium(true).build();

        when(userRepository.findByUsernameIgnoreCase(anyString())).thenReturn(Optional.of(user7));
        when(userRepository.findByUsernameIgnoreCase("carapino")).thenReturn(Optional.empty());

        UserLoginDTO userLoginDTO = UserLoginDTO.builder()
                .username("carapino")
                .password("Testpassword124#!")
                .build();

        boolean loggedUser = usersService.loginUser(userLoginDTO);

        assertFalse(loggedUser);
    }

    @Test
    void loginUserFailWithEmailServiceTest() {

        passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode("Testpassword124#!");

        Users user6 = Users.builder()
                .id(1L)
                .name("Curcu")
                .username("carapan")
                .email("email@gmail.com")
                .password(encodedPassword)
                .userGenre(UserGenre.PNTS)
                .profilePictureURL(null)
                .premium(true).build();

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user6));
        when(userRepository.findByEmail("email123@gmail.com")).thenReturn(Optional.empty());

        UserLoginDTO userLoginDTO = UserLoginDTO.builder()
                .email("email123@gmail.com")
                .password("Testpassword124#!")
                .build();

        boolean loggedUser = usersService.loginUser(userLoginDTO);

        assertFalse(loggedUser);
    }

    @Test
    void loginUserFailWithPasswordServiceTest() {

        passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode("Testpassword124#!");

        Users user6 = Users.builder()
                .id(1L)
                .name("Curcu")
                .username("carapan")
                .email("email@gmail.com")
                .password(encodedPassword)
                .userGenre(UserGenre.PNTS)
                .profilePictureURL(null)
                .premium(true).build();

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user6));

        UserLoginDTO userLoginDTO = UserLoginDTO.builder()
                .email("email@gmail.com")
                .password("Testpassword12456")
                .build();

        boolean loggedUser = usersService.loginUser(userLoginDTO);

        assertFalse(loggedUser);
    }

}
