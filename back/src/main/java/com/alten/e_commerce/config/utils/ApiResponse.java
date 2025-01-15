package com.alten.e_commerce.config.utils;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class ApiResponse {
    private Boolean success;
    private String message;
    private Object object;
    private HttpStatus statusCode;


    /**
     *
     * @param success
     * @param message
     * @param statusCode
     * @param object
     */
    public ApiResponse(Boolean success, String message, HttpStatus statusCode, Object object) {
        this.success = success;
        this.message = message;
        this.statusCode = statusCode;
        this.object = object;
    }

    /**
     *
     * @param success
     * @param message
     */
    public ApiResponse(Boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    /**
     *
     * @param allowedMethod
     */
    public static ApiResponse methodNotAllowedResponse(String allowedMethod){
        return new ApiResponse(false, "Method " + allowedMethod + " is required.", HttpStatus.METHOD_NOT_ALLOWED, null);
    }

    /**
     *
     * @param errorMessage
     */
    public static ApiResponse nullPointerExceptionResponse(String errorMessage){
        return new ApiResponse(false, "Null value has been detected {" + errorMessage + "}.", HttpStatus.BAD_REQUEST, null);
    }

    /**
     *
     * @param errorMessage
     */
    public static ApiResponse exceptionResponse(String errorMessage){
        return new ApiResponse(false, "An error has been occur and the content is {" + errorMessage + "}.", HttpStatus.INTERNAL_SERVER_ERROR, null);
    }

}
