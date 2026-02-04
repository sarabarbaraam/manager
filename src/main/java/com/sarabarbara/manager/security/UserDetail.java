package com.sarabarbara.manager.security;


import com.sarabarbara.manager.users.Users;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * UserDetails class.
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 05/01/2026
 */

public record UserDetail(Users user) implements UserDetails {

    /**
     * Get roles/authorities of the user.
     *
     * @return a collection of granted authorities
     */


    @Contract(" -> new")
    @Override
    public @Unmodifiable @NotNull Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()));
    }

    public Long getId() {
        return user.getId();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public boolean isEnabled() {
        return user.getActive();
    }
}
