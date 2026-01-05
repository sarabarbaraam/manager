package com.sarabarbara.manager.users;


import com.sarabarbara.manager.users.dtos.UsersDTO;
import com.sarabarbara.manager.users.dtos.CreateUserDTO;
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
