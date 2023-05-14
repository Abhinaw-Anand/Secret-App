package com.example.SpringInitial.Model.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class CreateAccountResponse
{

    private String response;

    public CreateAccountResponse(Long id)
    {
        this.response = "Account successfully created with ID :"+id;
    }
}
