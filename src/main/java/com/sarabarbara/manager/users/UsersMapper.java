package com.sarabarbara.manager.users;


import com.sarabarbara.manager.users.dtos.CreateUserDTO;
import com.sarabarbara.manager.users.dtos.UsersDTO;
import org.mapstruct.*;

import java.util.List;

/**
 * UsersMapper class.
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 30/12/2025
 */

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UsersMapper {

    Users toEntity(UserRequest request);

    UsersDTO toDTO(Users user);

    List<UsersDTO> toDTOList(List<Users> user);

    CreateUserDTO toCreateUserDTO(Users user);

    @Mapping(target = "name", source = "request.name")
    @Mapping(target = "username", source = "request.username")
    @Mapping(target = "email", source = "request.email")
    @Mapping(target = "password", source = "request.password")
    @Mapping(target = "genre", source = "request.genre")
    @Mapping(target = "profilePictureURL", source = "request.profilePictureURL")
    Users updateEntityFromRequest(UserRequest request, @MappingTarget Users existingUser);
}
