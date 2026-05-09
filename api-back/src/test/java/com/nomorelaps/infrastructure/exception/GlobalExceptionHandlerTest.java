package com.nomorelaps.infrastructure.exception;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import java.util.Map;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    @DisplayName("Should handle AuthenticationException (401)")
    void testHandleAuthenticationException() {
        ResponseEntity<Map<String, String>> response = handler.handleAuthenticationException(new BadCredentialsException("error"));
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Email or password incorrect", response.getBody().get("message"));
    }

    @Test
    @DisplayName("Should handle BusinessException (400)")
    void testHandleBusinessException() {
        ResponseEntity<Map<String, String>> response = handler.handleBusinessException(new IllegalArgumentException("invalid"));
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("invalid", response.getBody().get("message"));
    }

    @Test
    @DisplayName("Should handle GenericException (500)")
    void testHandleGenericException() {
        ResponseEntity<Map<String, String>> response = handler.handleGenericException(new RuntimeException("fatal"));
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("fatal", response.getBody().get("message"));
    }
}
