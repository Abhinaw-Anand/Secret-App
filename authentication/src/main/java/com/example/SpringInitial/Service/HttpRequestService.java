package com.example.SpringInitial.Service;

import com.example.SpringInitial.Constants.ExceptionsConstants;
import com.example.SpringInitial.Exceptions.CustomException;
import com.example.SpringInitial.Model.FeignRequests.GetPasswordByEmailAndTitleRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class HttpRequestService
{
    private PasswordSavingServiceProxy passwordSavingServiceProxy;
    private ObjectMapper objectMapper;


    public String makePasswordSavingServicePasswordSavingServiceRequest(String email, String title)
    {
        try
        {
            GetPasswordByEmailAndTitleRequest getPasswordByEmailAndTitleRequest = new GetPasswordByEmailAndTitleRequest(email, title);
            log.info("making a request  to localhost:8081/PasswordSavingService/getPasswordByEmailAndTitle with body:{} ", objectMapper.writeValueAsString(getPasswordByEmailAndTitleRequest));
            ResponseEntity<String> responseEntity = passwordSavingServiceProxy.getPasswordByEmailAndTitle(getPasswordByEmailAndTitleRequest);
            log.info("Response of localhost:8081/PasswordSavingService/getPasswordByEmailAndTitle: {} and ResponseStatus is: {}", responseEntity.getBody(), responseEntity.getStatusCode());

            return responseEntity.getBody();

        } catch (FeignException e)
        {
            log.info("Exception in calling localhost:8081/PasswordSavingService/getPasswordByEmailAndTitle {} ", e.status());
            log.error("Response body of localhost:8081/PasswordSavingService/getPasswordByEmailAndTitle is: {} and response status is:{}", e.contentUTF8(), e.status());
            throw new CustomException(ExceptionsConstants.DownstreamLocalhostPasswordSavingServiceGetPasswordByEmailAndTitle.getErrorCode()
                    , ExceptionsConstants.DownstreamLocalhostPasswordSavingServiceGetPasswordByEmailAndTitle.getStatusCode()
                    , String.format("localhost:8081/PasswordSavingService/getPasswordByEmailAndTitle is giving Error: %s", e.status()));

        } catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }


}
