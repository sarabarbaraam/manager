package com.sarabarbara.manager.security;


import com.sarabarbara.manager.users.Users;
import com.sarabarbara.manager.users.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * UserDetailsServiceImpl class.
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 05/01/2026
 */

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UsersRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Users user = usersRepository.findByUsernameIgnoreCase(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found: " + username));

        return new UserDetail(user);
    }
}
