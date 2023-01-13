package com.spring.blogapp.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.function.Supplier;

@Getter
public class DataNotFoundException extends RuntimeException {
    private String resource;
    private String field;
    private String data;

    public DataNotFoundException(String resource, String field, String data) {
        super(String.format("%s not found with %s: %s", resource, field, data));
        this.resource = resource;
        this.field = field;
        this.data = data;
    }
}
