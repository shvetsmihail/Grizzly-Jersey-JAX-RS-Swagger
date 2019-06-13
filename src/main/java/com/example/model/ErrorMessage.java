package com.example.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Schema(
        name = "ErrorMessage",
        description = "this is example of ErrorMessage object"
)
@Data
@AllArgsConstructor
public class ErrorMessage {
    private String errorMessage;
    private int errorCode;
}