package com.example.SpringInitial.Service;

import lombok.AllArgsConstructor;
import org.jasypt.encryption.StringEncryptor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EncryptionService
{


    private StringEncryptor encryptor;


    public String encryptValue(String text)
    {
        return encryptor.encrypt(text);
    }

    public String decryptValue(String text)
    {
        return encryptor.decrypt(text);
    }
}