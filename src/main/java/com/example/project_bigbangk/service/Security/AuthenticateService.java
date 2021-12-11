package com.example.project_bigbangk.service.Security;


import com.example.project_bigbangk.model.Client;
import com.example.project_bigbangk.repository.RootRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * This class can authorize an login email and password combination and authorize a token
 * created by Pieter Jan Bleichrodt
 * Creation date 12/3/2021
 */
@Service
public class AuthenticateService {
    RootRepository rootRepository;
    ITokenService tokenService;
    IHashService hashService;

    @Autowired
    public AuthenticateService(ITokenService tokenService, RootRepository rootRepository, IHashService hashService) {
        super();
        this.rootRepository = rootRepository;
        this.tokenService = tokenService;
        this.hashService = hashService;
    }

    public boolean authenticate(String email, String password) {
        Client client = rootRepository.findClientByEmail(email);
        if (client != null) {
            return hashService.hashCheck(password, client.getPassWord());
        }
        return false;
    }

    public boolean authenticate(String token) {
        return tokenService.authenticateToken(token);
    }
}