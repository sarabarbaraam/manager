package com.sarabarbara.manager.security.handlers;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.sarabarbara.manager.config.GlobalExceptionHandler;
import com.sarabarbara.manager.shared.ErrorResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * DelegatingAccessDeniedHandler class.
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 12/01/2026
 */

@RequiredArgsConstructor
@Component
public class DelegatingAccessDeniedHandler implements AccessDeniedHandler {

    private final GlobalExceptionHandler globalExceptionHandler;
    private final ObjectMapper mapper;

    @Override
    public void handle(HttpServletRequest request,
                       @NotNull HttpServletResponse response,
                       AccessDeniedException accessDeniedException)
            throws IOException {

        ResponseEntity<ErrorResponse> errorResponse =
                globalExceptionHandler.handleAccessDeniedException(accessDeniedException);

        response.setStatus(errorResponse.getStatusCode().value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        mapper.writeValue(response.getWriter(), errorResponse.getBody());
    }


}
