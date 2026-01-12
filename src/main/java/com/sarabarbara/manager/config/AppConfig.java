package com.sarabarbara.manager.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;

/**
 * AppConfig class.
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 06/01/2026
 */

@RequiredArgsConstructor
@Configuration
public class AppConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return mapper;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService,
                                                         PasswordEncoder passwordEncoder) {
        return new AuthenticationProvider() {

            @Override
            public Authentication authenticate(Authentication authentication) throws AuthenticationException {

                String username = authentication.getName();
                String password = authentication.getCredentials().toString();

                UserDetails user = userDetailsService.loadUserByUsername(username);

                if (!passwordEncoder.matches(password, user.getPassword())) {
                    throw new BadCredentialsException("Invalid credentials");
                }

                return new UsernamePasswordAuthenticationToken(
                        user,
                        null,
                        user.getAuthorities()
                );
            }

            @Override
            public boolean supports(Class<?> authentication) {

                return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);

            }
        };
    }

    @Bean
    public AuthenticationManager authenticationManager(@NotNull AuthenticationProvider provider) {
        return provider::authenticate;
    }

}
