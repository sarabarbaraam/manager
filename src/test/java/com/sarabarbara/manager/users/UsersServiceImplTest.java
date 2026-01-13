package com.sarabarbara.manager.users;


import com.sarabarbara.manager.infrastructure.external.zerobounce.EmailValidationResult;
import com.sarabarbara.manager.infrastructure.external.zerobounce.ZeroBounceClient;
import com.sarabarbara.manager.security.AuthService;
import com.sarabarbara.manager.subscriptions.SubscriptionsPlan;
import com.sarabarbara.manager.subscriptions.SubscriptionsPlanEnum;
import com.sarabarbara.manager.subscriptions.SubscriptionsPlanRepository;
import com.sarabarbara.manager.users.dtos.CreateUserDTO;
import com.sarabarbara.manager.users.dtos.UsersDTO;
import com.sarabarbara.manager.users.exceptions.UserNotFoundException;
import com.sarabarbara.manager.users.exceptions.UserValidateException;
import com.sarabarbara.manager.users.exceptions.UsersException;
import com.sarabarbara.manager.users.requestes.UpdateUserRequest;
import com.sarabarbara.manager.users.requestes.UserRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * UsersServiceImplTest class.
 * <p>
 * Tests for UsersServiceImpl.
 * <p>
 * Cobertura: createUser (ok, duplicated username, duplicated email, invalid password),
 * getUsers (ok, empty -> UsersException),
 * getUserByUsername (ok, not found -> UserNotFoundException),
 * updateUser (ok partial/total, username/email taken, not found),
 * deleteUser (ok, not found).
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 12/01/2026
 */

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class UsersServiceImplTest {

    @InjectMocks
    private UsersServiceImpl usersService;

    @Mock
    private UsersRepository userRepository;

    @Mock
    private SubscriptionsPlanRepository subscriptionsPlanRepository;

    @Mock
    private ZeroBounceClient zeroBounceClient;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @Mock
    private UsersMapper usersMapper;

    @Mock
    private AuthService authService;

    private Users existingUser;
    private PageRequest pageRequest;

    @BeforeEach
    void setUp() {
        pageRequest = PageRequest.of(0, 10);

        existingUser = new Users();
        existingUser.setId(1L);
        existingUser.setUsername("existing");
        existingUser.setEmail("existing@example.com");
        existingUser.setPassword("oldEncoded");
        existingUser.setActive(true);
    }

    // ---------------- createUser tests ----------------

    @Test
    void createUser_success() {

        // Arrange
        UserRequest request = mock(UserRequest.class);
        when(request.username()).thenReturn("newuser");
        when(request.email()).thenReturn("new@example.com");
        when(request.password()).thenReturn("Password1!");

        Users entityFromMapper = new Users();
        entityFromMapper.setUsername("newuser");
        entityFromMapper.setEmail("new@example.com");
        entityFromMapper.setPassword("Password1!");

        when(userRepository.findByUsernameIgnoreCase("newuser")).thenReturn(Optional.empty());
        when(userRepository.findByEmail("new@example.com")).thenReturn(Optional.empty());

        EmailValidationResult emailResult = mock(EmailValidationResult.class);
        when(zeroBounceClient.emailIsReal("new@example.com")).thenReturn(emailResult);
        when(emailResult.valid()).thenReturn(true);
        when(emailResult.verified()).thenReturn(true);

        when(usersMapper.toEntity(request)).thenReturn(entityFromMapper);

        when(passwordEncoder.encode("Password1!")).thenReturn("encodedPass");

        SubscriptionsPlan plan = new SubscriptionsPlan();
        plan.setId(10L);
        when(subscriptionsPlanRepository.findByName(SubscriptionsPlanEnum.FREE)).thenReturn(Optional.of(plan));

        // usersMapper.toCreateUserDTO will be called with the user instance (after save)
        CreateUserDTO dto = CreateUserDTO.builder()
                .username("newuser")
                .email("new@example.com")
                .build();
        when(usersMapper.toCreateUserDTO(any(Users.class))).thenReturn(dto);

        // Act
        CreateUserDTO result = usersService.createUser(request);

        // Assert
        assertNotNull(result);
        assertThat(result.username()).isEqualTo("newuser");
        assertThat(result.email()).isEqualTo("new@example.com");
    }

    @Test
    void createUser_duplicateUsername_throws() {
        UserRequest request = mock(UserRequest.class);
        when(request.username()).thenReturn("existing");
        when(userRepository.findByUsernameIgnoreCase("existing")).thenReturn(Optional.of(existingUser));

        assertThrows(UserValidateException.class, () -> usersService.createUser(request));
    }

    @Test
    void createUser_duplicateEmail_throws() {
        UserRequest request = mock(UserRequest.class);
        when(request.username()).thenReturn("another");
        when(request.email()).thenReturn("existing@example.com");
        when(request.password()).thenReturn("Password1!");
        when(userRepository.findByUsernameIgnoreCase("another")).thenReturn(Optional.empty());
        when(userRepository.findByEmail("existing@example.com")).thenReturn(Optional.of(existingUser));

        assertThrows(UserValidateException.class, () -> usersService.createUser(request));
    }

    @Test
    void createUser_invalidPassword_throws() {
        UserRequest request = mock(UserRequest.class);
        when(request.username()).thenReturn("u");
        when(request.password()).thenReturn("bad");

        assertThrows(UserValidateException.class, () -> usersService.createUser(request));

        verify(userRepository).findByUsernameIgnoreCase("u");
        verifyNoMoreInteractions(userRepository);
    }

    // ---------------- getUsers tests ----------------

    @Test
    void getUsers_success_returnsList() {
        List<Users> list = Collections.singletonList(existingUser);
        Page<Users> page = new PageImpl<>(list, pageRequest, list.size());

        when(userRepository.findAll(pageRequest)).thenReturn(page);

        UsersDTO dto = mock(UsersDTO.class);
        when(dto.username()).thenReturn(existingUser.getUsername());
        when(usersMapper.toDTOList(list)).thenReturn(Collections.singletonList(dto));

        List<UsersDTO> result = usersService.getUsers(0, 10);

        assertThat(result).hasSize(1);
        assertThat(result.getFirst().username()).isEqualTo("existing");
    }

    @Test
    void getUsers_empty_throwsUsersException() {
        Page<Users> emptyPage = new PageImpl<>(Collections.emptyList(), pageRequest, 0);
        when(userRepository.findAll(pageRequest)).thenReturn(emptyPage);

        assertThrows(UsersException.class, () -> usersService.getUsers(0, 10));
    }

    // ---------------- getUserByUsername tests ----------------

    @Test
    void getUserByUsername_success() {
        List<Users> list = Collections.singletonList(existingUser);
        Page<Users> page = new PageImpl<>(list, pageRequest, list.size());

        when(userRepository.findByUsernameContainingIgnoreCaseAndActiveTrue("existing", pageRequest)).thenReturn(page);

        UsersDTO dto = mock(UsersDTO.class);
        when(dto.username()).thenReturn("existing");
        when(usersMapper.toDTOList(list)).thenReturn(Collections.singletonList(dto));

        List<UsersDTO> result = usersService.getUserByUsername("existing", 0, 10);

        assertThat(result).hasSize(1);
        assertThat(result.getFirst().username()).isEqualTo("existing");
    }

    @Test
    void getUserByUsername_notFound_throws() {
        Page<Users> emptyPage = new PageImpl<>(Collections.emptyList(), pageRequest, 0);
        when(userRepository.findByUsernameContainingIgnoreCaseAndActiveTrue("nope", pageRequest)).thenReturn(emptyPage);

        assertThrows(UserNotFoundException.class, () -> usersService.getUserByUsername("nope", 0, 10));
    }

    // ---------------- updateUser tests ----------------

    @Test
    void updateUser_changeUsernameEmailPassword_success() {
        // Arrange
        UpdateUserRequest request = mock(UpdateUserRequest.class);
        when(request.username()).thenReturn("newname");
        when(request.email()).thenReturn("newmail@example.com");
        when(request.password()).thenReturn("Newpassword1!");

        when(authService.getCurrentUserId()).thenReturn(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));
        when(userRepository.findByUsernameIgnoreCase("newname")).thenReturn(Optional.empty());
        when(userRepository.findByEmail("newmail@example.com")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("Newpassword1!")).thenReturn("newEncoded");

        EmailValidationResult validation = mock(EmailValidationResult.class);
        when(validation.valid()).thenReturn(true);
        when(zeroBounceClient.emailIsReal("newmail@example.com"))
                .thenReturn(validation);

        // Mapper
        Users updatedUser = new Users();
        updatedUser.setId(existingUser.getId());
        updatedUser.setUsername("newname");
        updatedUser.setEmail("newmail@example.com");
        updatedUser.setPassword("newEncoded");

        when(usersMapper.updateEntityFromRequest(eq(request), any(Users.class)))
                .thenReturn(updatedUser);
        when(userRepository.save(updatedUser)).thenReturn(updatedUser);

        UsersDTO dto = mock(UsersDTO.class);
        when(dto.username()).thenReturn("newname");
        when(dto.email()).thenReturn("newmail@example.com");
        when(usersMapper.toDTO(any(Users.class))).thenReturn(dto);

        // Act
        UsersDTO result = usersService.updateUser(request);

        // Assert
        assertNotNull(result);
        assertThat(result.username()).isEqualTo("newname");
        assertThat(result.email()).isEqualTo("newmail@example.com");
    }

    @Test
    void updateUser_usernameTaken_throws() {
        UpdateUserRequest request = mock(UpdateUserRequest.class);
        when(request.username()).thenReturn("taken");
        when(authService.getCurrentUserId()).thenReturn(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));

        Users other = new Users();
        other.setId(2L);
        other.setUsername("taken");

        when(userRepository.findByUsernameIgnoreCase("taken")).thenReturn(Optional.of(other));

        assertThrows(UserValidateException.class, () -> usersService.updateUser(request));
    }

    @Test
    void updateUser_emailTaken_throws() {
        UpdateUserRequest request = mock(UpdateUserRequest.class);
        when(request.username()).thenReturn(null);
        when(request.email()).thenReturn("taken@example.com");
        when(authService.getCurrentUserId()).thenReturn(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));

        Users other = new Users();
        other.setId(3L);
        other.setEmail("taken@example.com");

        when(userRepository.findByEmail("taken@example.com")).thenReturn(Optional.of(other));

        assertThrows(UserValidateException.class, () -> usersService.updateUser(request));
    }

    @Test
    void updateUser_notFound_throws() {
        UpdateUserRequest request = mock(UpdateUserRequest.class);
        when(authService.getCurrentUserId()).thenReturn(99L);
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> usersService.updateUser(request));
    }

    // ---------------- deleteUser tests ----------------

    @Test
    void deleteUser_success_deactivates() {
        when(authService.getCurrentUserId()).thenReturn(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(Users.class))).thenAnswer(inv -> inv.getArgument(0));

        usersService.deleteUser();

        ArgumentCaptor<Users> captor = ArgumentCaptor.forClass(Users.class);
        verify(userRepository).save(captor.capture());
        Users saved = captor.getValue();
        assertFalse(saved.getActive());
    }

    @Test
    void deleteUser_notFound_throws() {
        when(authService.getCurrentUserId()).thenReturn(5L);
        when(userRepository.findById(5L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> usersService.deleteUser());
    }

}
