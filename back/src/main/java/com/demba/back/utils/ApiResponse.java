package com.demba.back.utils;

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
     * @param statusCode
     * @param object
     */
    public ApiResponse(Boolean success, String message, HttpStatus statusCode) {
        this.success = success;
        this.message = message;
        this.statusCode = statusCode;
    }

    /**
     *
     * @param message
     * @param statusCode
     */
    public static ApiResponse successResponse(String message, HttpStatus statusCode, Object object){
        return new ApiResponse(true, message, statusCode, object);
    }

    /**
     *
     * @param message
     * @param statusCode
     */
    public static ApiResponse successResponse(String message, HttpStatus statusCode){
        return new ApiResponse(true, message, statusCode);
    }

    /**
     *
     * @param errorMessage
     * @param statusCode
     */
    public static ApiResponse errorResponse(String errorMessage, HttpStatus statusCode){
        return new ApiResponse(false, errorMessage, statusCode);
    }

}
