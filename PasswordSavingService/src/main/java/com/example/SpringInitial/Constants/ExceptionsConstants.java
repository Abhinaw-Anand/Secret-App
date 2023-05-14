package com.example.SpringInitial.Constants;


import org.springframework.http.HttpStatus;

public enum ExceptionsConstants
{

    PasswordNotFoundException("Password is not found",HttpStatus.NOT_FOUND),
    ValidationFailed ("1001-Invalid-Request", HttpStatus.BAD_REQUEST),
    ErrorWhileProcessing("2001-Error-while-processing",HttpStatus.INTERNAL_SERVER_ERROR),
    UserNotFoundException("User is not found",HttpStatus.NOT_FOUND);



    private final String errorCode;
    private final HttpStatus statusCode;
    private ExceptionsConstants(String errorCode, HttpStatus statusCode)
    {
        this.errorCode=errorCode;
        this.statusCode=statusCode;
    }

    public String getErrorCode()
    {
        return errorCode;
    }


    public HttpStatus getStatusCode()
    {
        return statusCode;
    }
}
