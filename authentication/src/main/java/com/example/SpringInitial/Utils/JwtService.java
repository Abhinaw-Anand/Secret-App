package com.example.SpringInitial.Utils;

import com.example.SpringInitial.Constants.ExceptionsConstants;
import com.example.SpringInitial.Exceptions.CustomException;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class JwtService
{

    String secret = "6150645367566B5970337336763979244226452948404D6351655468576D5A7134743777217A25432A462D4A614E645267556A586E3272357538782F413F4428";
    String issuer = "Secret-app";

    public String createJwt(String email) throws JOSEException
    {
        Instant now = Instant.now();
        Date issueTime = Date.from(now);
        Date expirationTime = Date.from(now.plus(2, TimeUnit.DAYS.toChronoUnit()));

        JWTClaimsSet claims = new JWTClaimsSet.Builder()
                .issuer(issuer)
                .subject(email)
                .audience("Public")
                .issueTime(issueTime)
                .expirationTime(expirationTime)
                .build();

        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);
        Payload payload = new Payload(claims.toJSONObject());

        JWSSigner signer = new MACSigner(secret.getBytes());
        JWSObject jwsObject = new JWSObject(header, payload);
        jwsObject.sign(signer);

        final String jwtToken=jwsObject.serialize();
        return jwtToken;
    }

    public boolean validateToken(String jwtToken)
    {
        try
        {

            SignedJWT jwt = SignedJWT.parse(jwtToken);
            JWSVerifier verifier = new MACVerifier(secret);
            if (jwt.verify(verifier)==false)
            {
                //this only verifies if the token is tampered or not
                return false;
            }

            JWTClaimsSet claimsSet = jwt.getJWTClaimsSet();
            String subject = claimsSet.getSubject();
            Date expirationTime = claimsSet.getExpirationTime();

            if((new Date().after(expirationTime)))
            {

                //it checks if currentDate is after expiration time
                return false;
            }

            System.out.println("Subject: " + subject);
            System.out.println("Expiration Time: " + expirationTime);
        }
        catch (Exception e)
        {
            return false;
        }

        return true;
    }


    public String extractUserEmail(String jwtToken)
    {
        try
        {


            SignedJWT jwt = SignedJWT.parse(jwtToken);
            JWTClaimsSet claimsSet = jwt.getJWTClaimsSet();
            String email = claimsSet.getSubject();
            return email;
        }
        catch (Exception e)
        {
            throw new CustomException(ExceptionsConstants.BadCredentials.getErrorCode(),ExceptionsConstants.BadCredentials.getStatusCode(),"Bad Credentials");
        }
    }
}



