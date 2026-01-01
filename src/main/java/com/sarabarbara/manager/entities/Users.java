package com.sarabarbara.manager.entities;


import com.sarabarbara.manager.enums.GenreEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

/**
 * Users class.
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 30/12/2025
 */

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
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

    @ToString.Exclude
    @Column(nullable = false, length = 70)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(length = 1)
    private GenreEnum genre;

    @Column(name = "profile_picture_url", length = 500)
    private String profilePictureURL;

    /**
     * The premium status of the user
     */

    private Boolean premium;

    private Boolean active;

    /**
     * The trimPassword method trims whitespace from the password before persisting or updating.
     */

    @PrePersist
    @PreUpdate
    public void trimPassword() {

        this.password = this.password.trim();
    }

}
