package com.example.SpringInitial.Service;

import com.example.SpringInitial.Model.SecretAppUserAuth;
import com.example.SpringInitial.Repository.SecretAppUserAuthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SecretAppUserAuthUserDetailsService implements UserDetailsService
{

    @Autowired
    private SecretAppUserAuthRepository secretAppUserAuthUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {

        SecretAppUserAuth secretAppUserAuth = secretAppUserAuthUserRepository.findByEmail(username);
        if (secretAppUserAuth == null)
        {
            throw new UsernameNotFoundException("User details not found for the user : " + username);
        }
        return secretAppUserAuth;
    }
}