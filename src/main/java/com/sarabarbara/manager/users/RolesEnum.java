package com.sarabarbara.manager.users;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * UsersRoleEnum class.
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 05/01/2026
 */

@Getter
@AllArgsConstructor
@Schema(name = "Roles Enum", description = "Roles assigned to users")
public enum RolesEnum {

    USER("USER"),
    PRO("PRO"),
    ADMIN("ADMIN");

    private final String role;
}
