package com.example.SpringInitial.Controller;

import com.example.SpringInitial.Constants.ExceptionsConstants;
import com.example.SpringInitial.Exceptions.CustomException;
import com.example.SpringInitial.Model.Request.GetPasswordByEmailAndTitleRequest;
import com.example.SpringInitial.Model.Response.ExceptionResponse;
import com.example.SpringInitial.Repository.UserRepository;
import com.example.SpringInitial.Service.GetPasswordService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

@RestController
@RequestMapping("/PasswordSavingService")
@AllArgsConstructor
@Slf4j
public class Controller
{
    UserRepository userRepository;
    GetPasswordService getPasswordService;
    private Validator validator;

    private ObjectMapper objectMapper;
    @PostMapping("/getPasswordByEmailAndTitle")
    public ResponseEntity<Object> getPasswordByEmailAndTitle(@RequestBody GetPasswordByEmailAndTitleRequest getPasswordByEmailAndTitleRequest, HttpServletRequest request) throws Exception
    {
        try
        {
            log.info("Request received for /PasswordSavingService/getPasswordByEmailAndTitle is: {}",getPasswordByEmailAndTitleRequest);
            log.info("Request received for /PasswordSavingService/getPasswordByEmailAndTitle in JSON is: {}",objectMapper.writeValueAsString(getPasswordByEmailAndTitleRequest));
            Set<ConstraintViolation<GetPasswordByEmailAndTitleRequest>> violations = validator.validate(getPasswordByEmailAndTitleRequest);
            if (!violations.isEmpty())
            {
                StringBuilder message = new StringBuilder("Validation Failed for request /PasswordSavingService/PasswordSavingService ");
                for (ConstraintViolation<GetPasswordByEmailAndTitleRequest> violation : violations)
                {
                    log.info("Validation failed for path: {} for Violation: {} ", violation.getPropertyPath(), violation.getMessage());
                    message.append(String.format("Validation failed for path: %s for Violation: %s ,", violation.getPropertyPath(), violation.getMessage()));
                }
                throw new CustomException(ExceptionsConstants.ValidationFailed.getErrorCode(), ExceptionsConstants.ValidationFailed.getStatusCode(), message.toString());
            }
            String email = getPasswordByEmailAndTitleRequest.getEmail();
            String title = getPasswordByEmailAndTitleRequest.getTitle();
            return new ResponseEntity<>(getPasswordService.getPasswordByEmailAndTitle(email, title), HttpStatus.OK);
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
