package com.example.SpringInitial.Filter;


import com.example.SpringInitial.Utils.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Component
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter
{

    @Autowired
    public   JwtService jwtService;
    @Autowired
    private  UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException
    {

        try
        {

            final String authHeader = request.getHeader("Authorization");
            final String jwt;
            final String userEmail;
            if (authHeader == null || !authHeader.startsWith("Bearer "))
            {
                filterChain.doFilter(request,response);
                return;

            }


            jwt = authHeader.substring(7);


            if (jwtService.validateToken(jwt) == false)
                throw new BadCredentialsException("Invalid Token received!");
            else
            {
                userEmail = jwtService.extractUserEmail(jwt);
                if(userEmail==null)
                    throw new BadCredentialsException("Invalid Token received!");



                //SecurityContextHolder.getContext().getAuthentication();
                if (userEmail != null)
                {
                    UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
                filterChain.doFilter(request,response);


            }
        }
        catch (Exception e)
        {
            log.error("Authentication Exception :",e);
            throw new BadCredentialsException("Invalid Token received!");


        }

    }
}





