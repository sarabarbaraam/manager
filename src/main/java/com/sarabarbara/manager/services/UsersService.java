package com.sarabarbara.manager.services;


import com.sarabarbara.manager.dtos.users.CreateUserDTO;
import com.sarabarbara.manager.dtos.UsersDTO;
import com.sarabarbara.manager.exceptions.UserNotFoundException;
import com.sarabarbara.manager.requestes.UserRequest;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

/**
 * UsersService class.
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 30/12/2025
 */

@Schema(name = "Users Service", description = "Service for managing users")
public interface UsersService {

    CreateUserDTO createUser(UserRequest request);
    List<UsersDTO> getUsers();
    List<UsersDTO> getUserByUsername(String username, int page, int size) throws UserNotFoundException;
    UsersDTO updateUser(Long idUser, UserRequest request) throws UserNotFoundException;
    void deleteUser();
}
