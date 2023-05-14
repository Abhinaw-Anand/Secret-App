package com.example.SpringInitial.Controller;


import com.example.SpringInitial.Constants.ExceptionsConstants;
import com.example.SpringInitial.Exceptions.CustomException;
import com.example.SpringInitial.Model.PasswordModel;
import com.example.SpringInitial.Model.Request.AuthenticationRequest;
import com.example.SpringInitial.Model.Request.CreateAccountRequest;
import com.example.SpringInitial.Model.Request.GetPasswordByTitleRequest;
import com.example.SpringInitial.Model.Response.CreateAccountResponse;
import com.example.SpringInitial.Model.Response.ExceptionResponse;
import com.example.SpringInitial.Repository.SecretAppUserAuthRepository;
import com.example.SpringInitial.Repository.SubscriptionRepository;
import com.example.SpringInitial.Service.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Arrays;
import java.util.Set;


@RestController
@AllArgsConstructor
@RequestMapping("/secret-app-authentication")
@Slf4j
public class Controller
{

    private PasswordEncoder passwordEncoder;
    private SubscriptionRepository subscriptionRepository;
    private SecretAppUserAuthRepository secretAppUserAuthUserRepository;
    private AuthenticationService authenticationService;
    private KafkaProducerService kafkaProducerService;
    private ObjectMapper objectMapper;
    private final AuthenticationManager authenticationManager;
    private Validator validator;
    private UserDatabaseService userDatabaseService;
    private MeterRegistry meterRegistry;
    private PasswordSavingServiceProxy passwordSavingServiceProxy;
    private HttpRequestService httpRequestService;


    @GetMapping(path = "/login")
    public ResponseEntity<?> login()
    {


        return new ResponseEntity<>("Logged in SuccessFully", HttpStatus.OK);
    }

    @GetMapping(path = "/welcome")
    public ResponseEntity<?> welcome()
    {


        return new ResponseEntity<>("adsda", HttpStatus.OK);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<Object> authenticate(@RequestBody AuthenticationRequest request
    ) throws Exception
    {
        try
        {
            var response = authenticationService.authenticate(request);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch (CustomException customException)
        {
            log.error("Some error occurred with with message {} stacktrace {}", customException.getMessage(), Arrays.toString(Arrays.copyOfRange(customException.getStackTrace(), 0, 5)));

            ExceptionResponse exceptionResponse = new ExceptionResponse(customException.getExceptionCode(), customException.getMessage(), MDC.get("requestId"));
            return new ResponseEntity<>(exceptionResponse, customException.getResponseCode());
        }



    }


    @PostMapping(path = "/createAccount")
    private ResponseEntity<?> createAccount(@RequestBody CreateAccountRequest createAccountRequest)
    {
        try
        {
            Set<ConstraintViolation<CreateAccountRequest>> violations = validator.validate(createAccountRequest);
            if (!violations.isEmpty())
            {
                StringBuilder message = new StringBuilder("Validation Failed ");
                for (ConstraintViolation<CreateAccountRequest> violation : violations)
                {
                    log.info("Validation failed for path: {} for Violation: {} ", violation.getPropertyPath(), violation.getMessage());
                    message.append(String.format("Validation failed for path: %s for Violation: %s ,", violation.getPropertyPath(), violation.getMessage()));
                }
                throw new CustomException(ExceptionsConstants.ValidationFailed.getErrorCode(), ExceptionsConstants.ValidationFailed.getStatusCode(), message.toString());

            }
            CreateAccountResponse createAccountResponse = new CreateAccountResponse(userDatabaseService.createAccount(createAccountRequest));


            return new ResponseEntity<>(createAccountResponse, HttpStatus.CREATED);
        } catch (CustomException customException)
        {
            log.error("Some error occurred with with message {} stacktrace {}", customException.getMessage(), Arrays.toString(Arrays.copyOfRange(customException.getStackTrace(), 0, 5)));
            ExceptionResponse exceptionResponse = new ExceptionResponse(customException.getExceptionCode(), customException.getMessage(), MDC.get("requestId"));
            return new ResponseEntity<>(exceptionResponse, customException.getResponseCode());

        } catch (Exception exception)
        {
            log.error("Some error occurred with with message {} stacktrace {}", exception.getMessage(), Arrays.toString(Arrays.copyOfRange(exception.getStackTrace(), 0, 5)));

            ExceptionResponse exceptionResponse = new ExceptionResponse
                    (ExceptionsConstants.ErrorWhileProcessing.getErrorCode(), "Unknown error while processing ", MDC.get("requestId"));

            return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }


    @PostMapping(path = "/savePassword")
    private ResponseEntity<?> createAccount(@RequestBody PasswordModel passwordModel, HttpServletRequest request)
    {

        try
        {

            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            log.info("Request received is for  /secret-app-authentication/savePassword in json format is {}", objectMapper.writeValueAsString(passwordModel));
            log.info("Request received is for  /secret-app-authentication/savePassword  is {}", passwordModel.toString());
            passwordModel.setEmail(auth.getName());
            Set<ConstraintViolation<PasswordModel>> violations = validator.validate(passwordModel);

            log.info("Validating /secret-app-authentication/savePassword");
            if (!violations.isEmpty())
            {
                StringBuilder message = new StringBuilder("Validation Failed");
                for (ConstraintViolation<PasswordModel> violation : violations)
                {
                    log.info("Validation failed for path: {} for Violation: {} ", violation.getPropertyPath(), violation.getMessage());
                    message.append(String.format("Validation failed for path: %s for Violation: %s ,", violation.getPropertyPath(), violation.getMessage()));
                }
                throw new CustomException(ExceptionsConstants.ValidationFailed.getErrorCode(), ExceptionsConstants.ValidationFailed.getStatusCode(), message.toString());

            }

            log.info("Validation Successfully Passed for the /secret-app-authentication/savePassword request ");
            kafkaProducerService.sendMessage(objectMapper.writeValueAsString(passwordModel));
        } catch (CustomException customException)
        {
            ExceptionResponse exceptionResponse = new ExceptionResponse(customException.getExceptionCode(), customException.getMessage(), MDC.get("requestId"));
            return new ResponseEntity<>(exceptionResponse, customException.getResponseCode());

        } catch (Exception exception)
        {
            ExceptionResponse exceptionResponse = new ExceptionResponse
                    (ExceptionsConstants.ErrorWhileProcessing.getErrorCode(), "Unknown error while processing ", MDC.get("requestId"));
            return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);

        }

        return new ResponseEntity<>("Password Saved", HttpStatus.CREATED);
    }

    @PostMapping(path = "/saveNote")
    private ResponseEntity<Object> createNote(
            @RequestParam(name = "note") String note
    )
    {
        return new ResponseEntity<>("Password Saved", HttpStatus.CREATED);
    }


    @GetMapping("/getPasswordByEmailAndTitle")
    public ResponseEntity<?> getPasswordByEmailAndTitle(@RequestBody GetPasswordByTitleRequest getPasswordByTitleRequest)
    {

        try
        {
            String email = SecurityContextHolder.getContext().getAuthentication().getName();
            log.info("Request Received for /secret-app-authentication/getPasswordByEmailAndTitle is :" + getPasswordByTitleRequest);
            String password= httpRequestService.makePasswordSavingServicePasswordSavingServiceRequest(email, getPasswordByTitleRequest.getTitle());
            return new ResponseEntity<>(password, HttpStatus.OK);
        } catch (CustomException customException)
        {
            ExceptionResponse exceptionResponse = new ExceptionResponse(customException.getExceptionCode(), customException.getMessage(), MDC.get("requestId"));
            return new ResponseEntity<>(exceptionResponse, customException.getResponseCode());

        } catch (Exception exception)
        {
            ExceptionResponse exceptionResponse = new ExceptionResponse
                    (ExceptionsConstants.ErrorWhileProcessing.getErrorCode(), "Unknown error while processing ", MDC.get("requestId"));
            return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }
}
