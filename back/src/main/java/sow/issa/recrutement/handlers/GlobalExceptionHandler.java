package sow.issa.recrutement.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import sow.issa.recrutement.exceptions.ResourceAlreadyExistException;
import sow.issa.recrutement.exceptions.ResourceNotFoundException;
import sow.issa.recrutement.exceptions.UserNotAllowedException;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @Autowired
    private ObjectMapper objectMapper;

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Error invalidInput(MethodArgumentNotValidException e) {

        logger.error(e.getMessage());

        Error error = new Error();
        error.setCode(HttpStatus.UNPROCESSABLE_ENTITY.value());
        error.setMessage("unable to process the contained instructions");
        error.setErrors(e.getBindingResult().getFieldErrors().stream().map(objectError -> Map.of(objectError.getField(), objectError.getDefaultMessage())).collect(Collectors.toList()));
        return error;
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException.class)
    public Error notFound(ResourceNotFoundException e) {

        logger.error(e.getMessage());

        Error error = new Error();
        error.setCode(HttpStatus.NOT_FOUND.value());
        error.setMessage(e.getMessage());

        return error;
    }

    @ResponseStatus(value = HttpStatus.CONFLICT)
    @ExceptionHandler(ResourceAlreadyExistException.class)
    public Error conflict(ResourceAlreadyExistException e) {

        logger.error(e.getMessage());

        Error error = new Error();
        error.setCode(HttpStatus.CONFLICT.value());
        error.setMessage(e.getMessage());

        return error;
    }

    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Error invalidInput(final HttpMessageNotReadableException e) {

        logger.error("HttpMessageNotReadableException: {}", e.getMessage());

        Throwable rootCause = e;
        while(rootCause.getCause() != null) {
            rootCause = rootCause.getCause();
        }

        final Error error = new Error();
        error.setCode(HttpStatus.UNPROCESSABLE_ENTITY.value());
        error.setMessage(rootCause.getMessage());

        return error;
    }

    @ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE)
    @ExceptionHandler(BadCredentialsException.class)
    public Error badCredentials(BadCredentialsException e) {

        logger.error("badCredentials: {}", e.getMessage());

        Error error = new Error();
        error.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        error.setMessage(e.getMessage());

        return error;
    }

    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    @ExceptionHandler(UserNotAllowedException.class)
    public Error notAllowed(UserNotAllowedException e) {

        logger.error("notAllowed: {}", e.getMessage());

        Error error = new Error();
        error.setCode(HttpStatus.FORBIDDEN.value());
        error.setMessage(e.getMessage());

        return error;
    }

    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public Error internalError(Exception e) {

        logger.error("internalError", e);

        Error error = new Error();
        error.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        error.setMessage("An unexpected server error occurred.");

        return error;
    }

    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(ExpiredJwtException.class)
    public Error expiredJwtException(ExpiredJwtException e) {

        logger.error(e.getMessage());

        Error error = new Error();
        error.setCode(HttpStatus.UNAUTHORIZED.value());
        error.setMessage(e.getMessage());

        return error;
    }

    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(ConstraintViolationException.class)
    public Error constraintViolation(ConstraintViolationException e) {

        logger.error(e.getMessage());

        Error error = new Error();
        error.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        error.setMessage(e.getMessage());

        return error;
    }

    private Error decode(final String data) {

        try {
            return objectMapper.readValue(data, Error.class);
        } catch (final IOException e) {
            logger.warn("Cannot decode error received from distant server.", e);
            return null;
        }
    }

    static class Error {

        private Integer code;
        private String message;
        private List<Map<String, String>> errors;

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public List<Map<String, String>> getErrors() {
            return errors;
        }

        public void setErrors(List<Map<String, String>> errors) {
            this.errors = errors;
        }
    }
}
