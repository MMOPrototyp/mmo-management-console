package com.jukusoft.mmo.auth.controller;

import com.jukusoft.mmo.auth.exception.UserNotFoundException;
import com.jukusoft.mmo.auth.request.AuthentificationRequest;
import com.jukusoft.mmo.auth.response.JWTTokenResponse;
import com.jukusoft.mmo.auth.service.AuthentificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthentificationController {

    private static final Logger logger = LoggerFactory.getLogger(AuthentificationController.class);

    private AuthentificationService authenticationService;

    public AuthentificationController(AuthentificationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/api/login")
    public ResponseEntity<JWTTokenResponse> createCustomer(@RequestBody AuthentificationRequest request) {
        JWTTokenResponse tokenRes = authenticationService.generateJWTToken(request.getUsername(), request.getPassword());
        logger.info("create new token for user {}", request.getUsername());

        return new ResponseEntity<>(tokenRes, HttpStatus.OK);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

}
