package com.sarabarbara.manager.users;


import com.sarabarbara.manager.shared.GenreEnum;
import com.sarabarbara.manager.subscriptions.SubscriptionsPlan;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

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
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_user", nullable = false)
    private Long id;

    @Column(nullable = false, length = 45)
    private String name;

    @Column(nullable = false, length = 45, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private boolean emailVerified;

    @ToString.Exclude
    @Column(nullable = false, length = 70)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(length = 2)
    private GenreEnum genre;

    @Column(name = "profile_picture_url", length = 500)
    private String profilePictureURL;

    @Enumerated(EnumType.STRING)
    private RolesEnum role;

    @ManyToOne
    @JoinColumn(name = "current_plan_name", nullable = false)
    private SubscriptionsPlan currentPlan;

    private Boolean active;

    @PrePersist
    @PreUpdate
    public void trimPassword() {

        this.password = this.password.trim();
    }

}
