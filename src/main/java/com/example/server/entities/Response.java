package com.example.server.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Map;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data
@SuperBuilder
@JsonInclude(NON_NULL)
public class Response {
    protected LocalDateTime timestamps;
    protected int StatusCode;
    protected HttpStatus status;
    protected String message;
    protected String reason;
    protected String devMessage;
    protected Map<?,?> data;
}
