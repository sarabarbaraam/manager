package com.sarabarbara.manager.models;


import com.sarabarbara.manager.enums.Genre;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

/**
 * Users class
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 04/10/2024
 */

@Entity
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "users")
public class Users implements Serializable {

    /**
     * The serialVersionUID
     */

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * The id
     */

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * The name
     */

    @NonNull
    @Size(min = 3, max = 45, message = "The name must be between 3 and 45 characters")
    private String name;

    /**
     * The username
     */

    @NonNull
    @Size(min = 3, max = 20, message = "The name must be between 3 and 20 characters")
    @Pattern(regexp = "^\\w{3,20}$", message = "The user name must not have two consecutive underscores or periods, " +
            "ensure that the string does not end in a period or underscore.")
    private String username;

    /**
     * The password
     */

    @NonNull
    @Size(min = 8, max = 70, message = "The password must be 8 minimum")
    private String password;

    /**
     * The email
     */

    @NonNull
    @Email(message = "The email must be valid")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
    private String email;

    /**
     * The genre
     */

    @NonNull
    @Enumerated(EnumType.STRING)
    private Genre genre;

    /**
     * The profilePictureURL
     */

    @Column(name = "profile_pictureurl")
    private String profilePictureURL;

    /**
     * The premium
     */

    @NonNull
    @Column(name = "premium")
    private Boolean premium;

    /**
     * The trimPassword
     */

    @PrePersist
    @PreUpdate
    public void trimPassword() {
        this.password = this.password.trim();
    }

    /**
     * The equals
     *
     * @param o the o
     *
     * @return the equals
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Users users)) return false;
        return Objects.equals(getId(), users.getId()) && Objects.equals(getName(), users.getName()) && Objects.equals(getUsername()
                , users.getUsername())
                && Objects.equals(getPassword(), users.getPassword()) && Objects.equals(getEmail(), users.getEmail())
                && Objects.equals(getGenre(), users.getGenre()) && Objects.equals(getProfilePictureURL(),
                users.getProfilePictureURL())
                && Objects.equals(getPremium(), users.getPremium());
    }

    /**
     * The hashcode
     *
     * @return the hash
     */

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getUsername(), getPassword(), getEmail(), getGenre(),
                getProfilePictureURL(), getPremium());
    }

}