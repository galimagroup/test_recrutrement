package com.architech.test.product.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.IOException;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class StorageException extends RuntimeException {
    public StorageException(String message, IOException e) {
        super(message);
    }
}
