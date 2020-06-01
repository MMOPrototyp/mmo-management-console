package com.jukusoft.mmo.auth.service;

import com.jukusoft.mmo.auth.exception.UserNotFoundException;
import com.jukusoft.mmo.auth.response.JWTTokenResponse;
import com.jukusoft.mmo.data.dao.UserDAO;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthentificationService {

    private UserDAO accountRepository;
    private JwtTokenService jwtTokenService;
    private PasswordEncoder passwordEncoder;

    public AuthentificationService(UserDAO accountRepository, JwtTokenService jwtTokenService, PasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.jwtTokenService = jwtTokenService;
        this.passwordEncoder = passwordEncoder;
    }

    public JWTTokenResponse generateJWTToken(String username, String password) {
        return accountRepository.findOneByUsername(username)
                .filter(account -> passwordEncoder.matches(password, account.getPassword()))
                .map(account -> new JWTTokenResponse(jwtTokenService.generateToken(account.getId(), username)))
                .orElseThrow(() -> new UserNotFoundException("Credentials wrong"));
    }

}
