package com.sarabarbara.manager.users;


import com.sarabarbara.manager.shared.GenreEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

/**
 * Users class.
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 30/12/2025
 */

@Data
@Builder
@Entity
@Table(name = "users")
@Schema(name = "Users Entity", description = "Entity representing a user in the system")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idUser;

    @Column(nullable = false, length = 45)
    private String name;

    @Column(nullable = false, length = 45, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @ToString.Exclude
    @Column(nullable = false, length = 70)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(length = 1)
    private GenreEnum genre;

    @Column(name = "profile_picture_url", length = 500)
    private String profilePictureURL;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    private Set<RolesEnum> role;

    private Boolean active;

    @PrePersist
    @PreUpdate
    public void trimPassword() {

        this.password = this.password.trim();
    }

}
