package com.projectLudoteca.ludoteca.common.response;

import java.util.List;

public class ApiResponse<T> {

    private T resultData;
    private String errorCode;
    private String errorName;
    private String errorMessage;

    public ApiResponse() {}

    public ApiResponse(T resultData) {
        this.resultData = resultData;
    }

    public ApiResponse(String errorCode, String errorName, String errorMessage) {
        this.errorCode = errorCode;
        this.errorName = errorName;
        this.errorMessage = errorMessage;
    }

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(data);
    }

    public static <T> ApiResponse<T> error(String code, String name, String message) {
        return new ApiResponse<>(code, name, message);
    }

    public T getResultData() {
        return resultData;
    }

    public void setResultData(T resultData) {
        this.resultData = resultData;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorName() {
        return errorName;
    }

    public void setErrorName(String errorName) {
        this.errorName = errorName;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

}
