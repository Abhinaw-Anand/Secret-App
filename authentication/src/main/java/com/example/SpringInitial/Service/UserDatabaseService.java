package com.example.SpringInitial.Service;


import com.example.SpringInitial.Constants.ExceptionsConstants;
import com.example.SpringInitial.Exceptions.CustomException;
import com.example.SpringInitial.Model.Request.CreateAccountRequest;
import com.example.SpringInitial.Model.SecretAppUserAuth;
import com.example.SpringInitial.Model.Subscription;
import com.example.SpringInitial.Repository.SecretAppUserAuthRepository;
import com.example.SpringInitial.Repository.SubscriptionRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class UserDatabaseService
{
    private PasswordEncoder passwordEncoder;
    private SecretAppUserAuthRepository secretAppUserAuthRepository;
    private SubscriptionRepository subscriptionRepository;


    public long createAccount(CreateAccountRequest createAccountRequest)
    {
            Subscription subscription = Subscription.builder().subscription(createAccountRequest.getSubscription()).build();
            SecretAppUserAuth secretAppUserAuth = SecretAppUserAuth.builder().email(createAccountRequest.getEmail())
                    .password(passwordEncoder.encode(createAccountRequest.getPassword())).subscription(List.of(subscription)).build();
            subscription.setSecretAppUserAuth(secretAppUserAuth);

            if (secretAppUserAuthRepository.findByEmail(createAccountRequest.getEmail()) != null)
            {
                log.info("User email Already Exists");
                throw new CustomException(ExceptionsConstants.UserAlreadyExists.getErrorCode(), ExceptionsConstants.UserAlreadyExists.getStatusCode(),
                        String.format("user with email %s already exists", createAccountRequest.getEmail()));

            } else
            {
                SecretAppUserAuth secretAppUserAuth1 = secretAppUserAuthRepository.save(secretAppUserAuth);
                subscriptionRepository.save(subscription);
                return secretAppUserAuth1.getId();
            }


        }

    }



