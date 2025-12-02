package com.projectLudoteca.ludoteca.common.exception;

import com.projectLudoteca.ludoteca.common.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Trata exceções genéricas de runtime (ex: IllegalArgumentException, IllegalStateException, etc.)
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponse<Void>> handleRuntimeException(RuntimeException ex) {
        ApiResponse<Void> response = new ApiResponse<>();
        response.setErrorCode(String.valueOf(HttpStatus.BAD_REQUEST.value()));
        response.setErrorName("RuntimeException");
        response.setErrorMessage(ex.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    /**
     * Trata erros de autenticação (ex: login inválido)
     */
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiResponse<Void>> handleBadCredentials(BadCredentialsException ex) {
        ApiResponse<Void> response = new ApiResponse<>();
        response.setErrorCode(String.valueOf(HttpStatus.UNAUTHORIZED.value()));
        response.setErrorName("BadCredentials");
        response.setErrorMessage("Credenciais inválidas");

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    /**
     * Trata erros de validação em DTOs anotados com @Valid
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> handleValidationErrors(MethodArgumentNotValidException ex) {
        String errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .collect(Collectors.joining(", "));

        ApiResponse<Void> response = new ApiResponse<>();
        response.setErrorCode(String.valueOf(HttpStatus.BAD_REQUEST.value()));
        response.setErrorName("ValidationError");
        response.setErrorMessage("Erro de validação: " + errors);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    /**
     * Trata exceções genéricas não previstas.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleGenericException(Exception ex) {
        ApiResponse<Void> response = new ApiResponse<>();
        response.setErrorCode(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
        response.setErrorName("InternalServerError");
        response.setErrorMessage("Erro interno no servidor: " + ex.getMessage());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    /**
     * Trata exceções de negócio personalizadas.
     */
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiResponse<Void>> handleBusinessException(BusinessException ex) {
        ApiResponse<Void> response = new ApiResponse<>();
        response.setErrorCode(ex.getErrorCode());
        response.setErrorName("BusinessException");
        response.setErrorMessage(ex.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }


}
