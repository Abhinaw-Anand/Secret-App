package com.example.SpringInitial.Model.Response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExceptionResponse
{

    String errorCode;
    String message;
    String requestId;

}
