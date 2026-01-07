package com.sarabarbara.manager.users;


import com.sarabarbara.manager.users.dtos.UsersDTO;
import com.sarabarbara.manager.users.dtos.CreateUserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

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

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "currentPlan", ignore = true)
    @Mapping(target = "active", ignore = true)

    Users toEntity(UserRequest request);
    UsersDTO toDTO(Users user);
    List<UsersDTO> toDTOList(List<Users> user);
    CreateUserDTO toCreateUserDTO(Users user);
}
