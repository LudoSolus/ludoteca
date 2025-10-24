package com.projectLudoteca.ludoteca.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {

    private boolean success;
    private String message;
    private T data;
    private int status;
    private String path; // opcional: útil para debugging no frontend

    public ApiResponse(boolean success, String message, T data, HttpStatus status, String path) {
        this.success = success;
        this.message = message;
        this.data = data;
        this.status = status.value();
        this.path = path;
    }

    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(true, message, data, HttpStatus.OK, null);
    }

    public static <T> ApiResponse<T> created(String message, T data) {
        return new ApiResponse<>(true, message, data, HttpStatus.CREATED, null);
    }

    public static <T> ApiResponse<T> error(String message, HttpStatus status) {
        return new ApiResponse<>(false, message, null, status, null);
    }

    // Getters e Setters
    public boolean isSuccess() { return success; }
    public String getMessage() { return message; }
    public T getData() { return data; }
    public int getStatus() { return status; }
    public String getPath() { return path; }

    public void setPath(String path) { this.path = path; }
}
