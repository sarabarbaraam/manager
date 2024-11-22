package com.sarabarbara.manager.mappers;

import com.sarabarbara.manager.dto.users.UserCreateDTO;
import com.sarabarbara.manager.dto.users.UserSearchDTO;
import com.sarabarbara.manager.dto.users.UserUpdateDTO;
import com.sarabarbara.manager.enums.UserGenre;
import com.sarabarbara.manager.models.users.Users;
import lombok.Builder;

import java.util.List;

/**
 * UsersMapper class
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 16/11/2024
 */

@Builder
public class UsersMapper {

    /**
     * The private constructor
     */

    private UsersMapper() {
    }

    /**
     * The {@link UserCreateDTO} mapper
     *
     * @param name              the name
     * @param username          the username
     * @param email             the email
     * @param userGenre         the userGenre
     * @param profilePictureURL the profile picture url
     * @param premium           the premium
     *
     * @return the mapper of UserCreateDTO
     */

    public static UserCreateDTO toUserCreateDTOMapper(String name, String username, String email, UserGenre userGenre,
                                                      String profilePictureURL, boolean premium) {

        return UserCreateDTO.builder()
                .name(name)
                .username(username)
                .email(email)
                .userGenre(userGenre)
                .profilePictureURL(profilePictureURL)
                .premium(premium)
                .build();
    }

    /**
     * the {@link UserSearchDTO} mapper
     *
     * @param searchedUser the searchedUser
     *
     * @return the mapper of the list of UserSearchDTO
     */

    public static List<UserSearchDTO> toUserSearchDTOMapper(List<Users> searchedUser) {

        return searchedUser.stream()
                .map(user -> UserSearchDTO.builder()
                        .username(user.getUsername())
                        .profilePictureURL(user.getProfilePictureURL())
                        .build())
                .toList();
    }

    /**
     * The {@link UserUpdateDTO} mapper
     *
     * @param name              the name
     * @param username          the username
     * @param email             the email
     * @param userGenre         the userGenre
     * @param profilePictureURL the profile picture url
     * @param premium           the premium
     *
     * @return the mapper of UserUpdateDTO
     */
    public static UserUpdateDTO toUserUpdateDTOMapper(String name, String username, String email, UserGenre userGenre,
                                                      String profilePictureURL, boolean premium) {

        return UserUpdateDTO.builder()
                .name(name)
                .username(username)
                .email(email)
                .userGenre(userGenre)
                .profilePictureURL(profilePictureURL)
                .premium(premium)
                .build();
    }

}
