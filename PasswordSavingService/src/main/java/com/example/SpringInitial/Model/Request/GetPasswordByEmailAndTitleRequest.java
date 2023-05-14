package com.example.SpringInitial.Model.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetPasswordByEmailAndTitleRequest
{

    @NotNull
    private String email;
    @NotNull
    private String title;
}
