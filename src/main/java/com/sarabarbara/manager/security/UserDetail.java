package com.sarabarbara.manager.security;


import com.sarabarbara.manager.users.Users;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

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
     * @return an empty list as no roles are defined
     */

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return user.getRole()
                .stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
                .collect(Collectors.toList());
    }

    public Long getId() {
        return user.getIdUser();
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
