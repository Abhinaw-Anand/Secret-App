package com.example.SpringInitial.Constants;


import org.springframework.http.HttpStatus;

public enum ExceptionsConstants
{

    ValidationFailed ("1001-Invalid-Request", HttpStatus.BAD_REQUEST),
    ErrorWhileProcessing("2001-Error-while-processing",HttpStatus.INTERNAL_SERVER_ERROR),
    UserAlreadyExists("3001-User-already-Exists", HttpStatus.BAD_REQUEST),
    DownStreamApiException("Downstream-API-request-exception",HttpStatus.INTERNAL_SERVER_ERROR),
    DownstreamLocalhostPasswordSavingServiceGetPasswordByEmailAndTitle("localhost:8081/PasswordSavingService/getPasswordByEmailAndTitle",HttpStatus.INTERNAL_SERVER_ERROR),
    BadCredentials("Bad-Credentials",HttpStatus.UNAUTHORIZED);
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
