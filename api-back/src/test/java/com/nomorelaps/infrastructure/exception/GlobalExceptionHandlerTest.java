package com.nomorelaps.infrastructure.exception;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

/**
 * Unit tests for GlobalExceptionHandler.
 * Centralizes testing of exception mapping to standardized JSON error responses.
 * Adheres to the New Backend Test Refactoring Plan for granularity and readability.
 */
class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler exceptionHandler;

    @BeforeEach
    void setUp() {
        exceptionHandler = new GlobalExceptionHandler();
    }


    @Test
    @DisplayName("handleAuthenticationException - BadCredentials: Should return 401 status")
    void handleAuthenticationException_BadCredentials_ShouldReturn401Status() {
        BadCredentialsException credentialsException = new BadCredentialsException("Failed");
        ResponseEntity<Map<String, String>> errorResponse = exceptionHandler.handleAuthenticationException(credentialsException);
        assertEquals(HttpStatus.UNAUTHORIZED, errorResponse.getStatusCode());
    }

    @Test
    @DisplayName("handleAuthenticationException - BadCredentials: Should return hardcoded message")
    void handleAuthenticationException_BadCredentials_ShouldReturnCorrectMessage() {
        BadCredentialsException credentialsException = new BadCredentialsException("Failed");
        ResponseEntity<Map<String, String>> errorResponse = exceptionHandler.handleAuthenticationException(credentialsException);
        assertEquals("Email or password incorrect", errorResponse.getBody().get("message"));
    }

    @Test
    @DisplayName("handleAuthenticationException - generic: Should return 401 status")
    void handleAuthenticationException_GenericAuth_ShouldReturn401Status() {
        AuthenticationException genericAuthException = new AuthenticationException("Error") {};
        ResponseEntity<Map<String, String>> errorResponse = exceptionHandler.handleAuthenticationException(genericAuthException);
        assertEquals(HttpStatus.UNAUTHORIZED, errorResponse.getStatusCode());
    }


    @Test
    @DisplayName("handleValidationException - Single field: Should map error correctly")
    void handleValidationException_SingleField_ShouldMapFieldNameAndMessage() {
        MethodArgumentNotValidException validationException = mock(MethodArgumentNotValidException.class);
        BindingResult mockBindingResult = mock(BindingResult.class);
        FieldError emailFieldError = new FieldError("user", "email", "invalid format");

        when(validationException.getBindingResult()).thenReturn(mockBindingResult);
        when(mockBindingResult.getFieldErrors()).thenReturn(List.of(emailFieldError));

        ResponseEntity<Map<String, String>> errorResponse = exceptionHandler.handleValidationException(validationException);
        
        assertEquals("invalid format", errorResponse.getBody().get("email"));
        assertEquals("400", errorResponse.getBody().get("status"));
    }

    @Test
    @DisplayName("handleValidationException - Multiple fields: Should map all errors")
    void handleValidationException_MultipleFields_ShouldMapAllErrors() {
        MethodArgumentNotValidException validationException = mock(MethodArgumentNotValidException.class);
        BindingResult mockBindingResult = mock(BindingResult.class);
        FieldError emailError = new FieldError("user", "email", "invalid");
        FieldError passwordError = new FieldError("user", "password", "short");

        when(validationException.getBindingResult()).thenReturn(mockBindingResult);
        when(mockBindingResult.getFieldErrors()).thenReturn(List.of(emailError, passwordError));

        ResponseEntity<Map<String, String>> errorResponse = exceptionHandler.handleValidationException(validationException);
        
        assertEquals("invalid", errorResponse.getBody().get("email"));
        assertEquals("short", errorResponse.getBody().get("password"));
    }


    @Test
    @DisplayName("handleBusinessException - IllegalArgument: Should return 400 and message")
    void handleBusinessException_IllegalArgument_ShouldReturn400AndMessage() {
        IllegalArgumentException businessArgumentException = new IllegalArgumentException("Business Error");
        ResponseEntity<Map<String, String>> errorResponse = exceptionHandler.handleBusinessException(businessArgumentException);

        assertEquals(HttpStatus.BAD_REQUEST, errorResponse.getStatusCode());
        assertEquals("Business Error", errorResponse.getBody().get("message"));
    }

    @Test
    @DisplayName("handleBusinessException - IllegalState: Should return 400 and message")
    void handleBusinessException_IllegalState_ShouldReturn400AndMessage() {
        IllegalStateException stateException = new IllegalStateException("State Error");
        ResponseEntity<Map<String, String>> errorResponse = exceptionHandler.handleBusinessException(stateException);

        assertEquals(HttpStatus.BAD_REQUEST, errorResponse.getStatusCode());
        assertEquals("State Error", errorResponse.getBody().get("message"));
    }


    @Test
    @DisplayName("handleGenericException - Unexpected: Should return 500 status")
    void handleGenericException_Unexpected_ShouldReturn500Status() {
        Exception fatalException = new Exception("Fatal error");
        ResponseEntity<Map<String, String>> errorResponse = exceptionHandler.handleGenericException(fatalException);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, errorResponse.getStatusCode());
    }

    @Test
    @DisplayName("handleGenericException - Unexpected: Should return exception message")
    void handleGenericException_Unexpected_ShouldReturnExceptionMessage() {
        Exception fatalException = new Exception("Fatal error");
        ResponseEntity<Map<String, String>> errorResponse = exceptionHandler.handleGenericException(fatalException);
        assertEquals("Fatal error", errorResponse.getBody().get("message"));
    }
}
