package com.sarabarbara.manager.users;


import com.sarabarbara.manager.users.dtos.CreateUserDTO;
import com.sarabarbara.manager.users.dtos.UsersDTO;
import com.sarabarbara.manager.users.exceptions.UserNotFoundException;

import java.util.List;

/**
 * UsersService class.
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 30/12/2025
 */

public interface UsersService {

    CreateUserDTO createUser(UserRequest request);
    List<UsersDTO> getUsers(int page, int size);
    List<UsersDTO> getUserByUsername(String username, int page, int size) throws UserNotFoundException;
    UsersDTO updateUser(Long idUser, UserRequest request) throws UserNotFoundException;
    void deleteUser();
}
