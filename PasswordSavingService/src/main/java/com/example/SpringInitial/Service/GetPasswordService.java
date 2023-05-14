package com.example.SpringInitial.Service;

import com.example.SpringInitial.Constants.ExceptionsConstants;
import com.example.SpringInitial.Exceptions.CustomException;
import com.example.SpringInitial.Model.User;
import com.example.SpringInitial.Repository.UserRepository;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class GetPasswordService
{
    UserRepository userRepository;
    EncryptionService encryptionService;

    @Retry(name = "getPasswordByEmailAndTitle")
    public String getPasswordByEmailAndTitle(String email,String title)
    {


        User user=userRepository.findByEmail(email);
        if(user==null)
            throw new CustomException(ExceptionsConstants.UserNotFoundException.getErrorCode()
                    ,ExceptionsConstants.UserNotFoundException.getStatusCode(),"Error User is not found");
        for (int i = 0; i < user.getPasswords().size(); i++)
        {
            if(user.getPasswords().get(i).getTitle().equals(title))
            {
                String encryptedText=user.getPasswords().get(i).getPassword();
                String decryptedText=encryptionService.decryptValue(encryptedText);
                return decryptedText;

            }
        }
        throw new CustomException(ExceptionsConstants.PasswordNotFoundException.getErrorCode()
                ,ExceptionsConstants.PasswordNotFoundException.getStatusCode(),"Error Title is not found");


    }


}
