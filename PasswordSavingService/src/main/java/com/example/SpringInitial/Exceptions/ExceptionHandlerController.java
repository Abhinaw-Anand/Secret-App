package com.example.SpringInitial.Exceptions;//package com.example.SpringInitial.Exceptions;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//
//import javax.servlet.http.HttpServletRequest;
//
//@ControllerAdvice
//public class ExceptionHandlerController {
//
//    @ExceptionHandler(BadCredentialsException.class)
//    public ResponseEntity<Object> handleBadCredentialsException() {
//        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
//    }
//}
