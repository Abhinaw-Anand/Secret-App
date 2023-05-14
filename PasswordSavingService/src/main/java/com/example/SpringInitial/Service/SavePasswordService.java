package com.example.SpringInitial.Service;

import com.example.SpringInitial.Model.Password;
import com.example.SpringInitial.Model.PasswordModel;
import com.example.SpringInitial.Model.User;
import com.example.SpringInitial.Repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@AllArgsConstructor
public class SavePasswordService
{

    UserRepository userRepository;
    EncryptionService encryptionService;


    public void savePasswordToMongoDB(PasswordModel passwordModel)
    {
        User user = userRepository.findByEmail(passwordModel.getEmail());
        Password password = new Password();
        if (user == null)
        {
            user = new User();
            user.setEmail(passwordModel.getEmail());
            ArrayList<Password> passwords = new ArrayList<>();
            user.setPasswords(passwords);
        }
        password.setPassword(encryptionService.encryptValue(passwordModel.getPassword()));
        password.setTitle(passwordModel.getTitle());

        for (Password pass : user.getPasswords())
        {
            if (pass.getTitle().equals(passwordModel.getTitle()))
            {
                pass.setPassword(encryptionService.encryptValue(passwordModel.getPassword()));
                userRepository.save(user);
                return;
            }
        }

        user.getPasswords().add(password);
        userRepository.save(user);

    }
}
