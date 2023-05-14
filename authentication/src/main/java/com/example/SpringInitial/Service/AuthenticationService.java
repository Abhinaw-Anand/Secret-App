package com.example.SpringInitial.Service;

import com.example.SpringInitial.Constants.ExceptionsConstants;
import com.example.SpringInitial.Exceptions.CustomException;
import com.example.SpringInitial.Model.Request.AuthenticationRequest;
import com.example.SpringInitial.Model.Response.AuthenticationResponse;
import com.example.SpringInitial.Repository.SecretAppUserAuthRepository;
import com.example.SpringInitial.Utils.JwtService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthenticationService {
  private final SecretAppUserAuthRepository repository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;


  public AuthenticationResponse authenticate(AuthenticationRequest request) throws Exception {




    var user = repository.findByEmailIgnoreCase(request.getEmail());


    if(user.isEmpty())
    {
      throw new CustomException(ExceptionsConstants.ValidationFailed.getErrorCode(),ExceptionsConstants.ValidationFailed.getStatusCode(),"Validation Exception Occurred");
    }
    var authenticated=passwordEncoder.matches(request.getPassword(),user.get().getPassword());
    if(authenticated==false)
    {

      throw new CustomException(ExceptionsConstants.ValidationFailed.getErrorCode(),ExceptionsConstants.ValidationFailed.getStatusCode(),"Validation Exception Occurred");
    }

    var jwtToken = jwtService.createJwt(user.get().getEmail());
    return AuthenticationResponse.builder()
        .token(jwtToken)
        .build();
  }
}
