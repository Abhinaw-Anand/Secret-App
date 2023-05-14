package com.example.SpringInitial.Model.FeignRequests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetPasswordByEmailAndTitleRequest
{

   private String email;
    private String title;
}
