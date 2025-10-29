package com.projectLudoteca.ludoteca.common.response;

import java.util.List;

public class ApiResponse<T> {

    private T resultData;
    private List<T> resultList;
    private String errorCode;
    private String errorName;
    private String errorMessage;
    private Integer page;
    private Integer totalPages;

    public ApiResponse() {}

    public ApiResponse(T resultData) {
        this.resultData = resultData;
    }

    public ApiResponse(List<T> resultList, Integer page, Integer totalPages) {
        this.resultList = resultList;
        this.page = page;
        this.totalPages = totalPages;
    }

    public ApiResponse(String errorCode, String errorName, String errorMessage) {
        this.errorCode = errorCode;
        this.errorName = errorName;
        this.errorMessage = errorMessage;
    }

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(data);
    }

    public static <T> ApiResponse<T> success(List<T> list, int page, int totalPages) {
        return new ApiResponse<>(list, page, totalPages);
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

    public List<T> getResultList() {
        return resultList;
    }

    public void setResultList(List<T> resultList) {
        this.resultList = resultList;
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

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }
}
