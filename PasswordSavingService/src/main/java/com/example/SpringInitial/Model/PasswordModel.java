package com.example.SpringInitial.Model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;


@Data
@AllArgsConstructor
@Builder
public class PasswordModel
{

    @NotNull
    @Email(message = "Invalid Email")
    @NotBlank(message =" Email Should not be blank")
    @Length(min = 1,max = 100,message = "length should be 0<x<100")
    private String email;
    @NotNull
    @NotBlank(message = "title is mandatory")
    private String title;
    @NotNull
    @NotBlank(message = "Password is mandatory")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,200}$",message ="Password must contain at least one digit [0-9]." +
            "Password must contain at least one lowercase Latin character [a-z].  Password must contain at least one uppercase Latin character [A-Z]. " +
            "Password must contain at least one special character like ! @ # & ( ). " +
            "Password must contain a length of at least 8 characters and a maximum of 200 characters.:")
    private  String password;

}
