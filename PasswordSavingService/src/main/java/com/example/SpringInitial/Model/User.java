package com.example.SpringInitial.Model;


import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Document(collection = "User")
@Data
public class User
{
    @Id
    String id;
    @Indexed(unique = true)
    String email;
    ArrayList<Password> passwords;
}
