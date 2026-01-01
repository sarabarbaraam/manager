package com.sarabarbara.manager.utils.mappers;


import com.sarabarbara.manager.dtos.UsersDTO;
import com.sarabarbara.manager.dtos.users.CreateUserDTO;
import com.sarabarbara.manager.entities.Users;
import com.sarabarbara.manager.requestes.UserRequest;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * UsersMapper class.
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 30/12/2025
 */

@Mapper(componentModel = "spring")
public interface UsersMapper {

    Users toEntity(UserRequest request);
    UsersDTO toDTO(Users user);
    List<UsersDTO> toDTOList(List<Users> user);
    CreateUserDTO toCreateUserDTO(Users user);
}
