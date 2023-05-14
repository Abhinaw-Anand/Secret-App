package com.example.SpringInitial.Exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public class CustomException extends RuntimeException
{



    String exceptionCode;
    HttpStatus responseCode;
    String message;
}
