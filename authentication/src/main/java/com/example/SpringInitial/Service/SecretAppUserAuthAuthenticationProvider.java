package com.example.SpringInitial.Service;

import com.example.SpringInitial.Model.SecretAppUserAuth;
import com.example.SpringInitial.Repository.SecretAppUserAuthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;



@Component
public class SecretAppUserAuthAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	private SecretAppUserAuthRepository secretAppUserAuthRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public Authentication authenticate(Authentication authentication) {
		String username = authentication.getName();
		String pwd = authentication.getCredentials().toString();
		SecretAppUserAuth secretAppUserAuth = secretAppUserAuthRepository.findByEmail(username);
		if (secretAppUserAuth!=null) {
			if (passwordEncoder.matches(pwd, secretAppUserAuth.getPassword())) {
				List<GrantedAuthority> authorities = new ArrayList<>();
				for (int i = 0; i < secretAppUserAuth.getSubscription().size(); i++)
				{
					authorities.add(new SimpleGrantedAuthority(secretAppUserAuth.getSubscription().get(i).getSubscription()));
				}

				return new UsernamePasswordAuthenticationToken(username, pwd,authorities);
			} else {
				throw new BadCredentialsException("Invalid password!");
			}
		}else {
			throw new BadCredentialsException("No user registered with this details!");
		}
	}
	


	@Override
	public boolean supports(Class<?> authenticationType) {
		return authenticationType.equals(UsernamePasswordAuthenticationToken.class);
	}
}
