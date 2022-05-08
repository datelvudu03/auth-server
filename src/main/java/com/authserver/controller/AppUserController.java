package com.authserver.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.authserver.entity.AppUser;
import com.authserver.entity.JwtToken;
import com.authserver.exceptionHandler.AppUserNotFoundException;
import com.authserver.repository.AppUserRepository;
import org.springframework.hateoas.EntityModel;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.springframework.hateoas.server.core.WebHandler.linkTo;


@RestController
@RequestMapping("api/users")

public class AppUserController {
    private final AppUserRepository appUserRepository;
    private final List<JwtToken> tokens;

    public AppUserController(AppUserRepository appUserRepository, List<JwtToken> tokens) {
        this.appUserRepository = appUserRepository;
        this.tokens = tokens;
    }

    @GetMapping("/all")
    List<AppUser> all(){
        return appUserRepository.findAll();
    }

    @GetMapping("/tokens")
    List<JwtToken> allTokens(){
        List<JwtToken> tempTokens = new ArrayList<>();
        for (JwtToken i: tokens) {
            String token = i.getToken();
            try {
                Algorithm algorithm = Algorithm.HMAC256("43f3b8fd2bab8815df2db818d08eeac0"); //use more secure key
                JWTVerifier verifier = JWT.require(algorithm)
                        .withIssuer("datudy")
                        .withClaim()
                        .build(); //Reusable verifier instance
                DecodedJWT jwt = verifier.verify(token);
            } catch (JWTVerificationException exception){
                //Invalid signature/claims
            }

        }
    }

    @GetMapping("/userGet/{username}/{password}")
    List<AppUser> getUser(@PathVariable String username,@PathVariable String password){
        return appUserRepository.findByUsernameAndPassword(username,password);
    }

    @GetMapping("/userGetJwt/{username}/{password}")
    EntityModel<JwtToken> getToken(@PathVariable String username,@PathVariable String password){
        List<AppUser> appUsers = appUserRepository.findByUsernameAndPassword(username,password);
        if (appUsers.isEmpty()){
            throw new AppUserNotFoundException(username);
        }
        AppUser appUser = appUsers.get(0);
        String token = null;
        try {
            Algorithm algorithm = Algorithm.HMAC256("43f3b8fd2bab8815df2db818d08eeac0");
            token = JWT.create().withIssuer("datudy")
                    .withClaim("userId", appUser.getId())
                    .withClaim("userName", appUser.getUsername())
                    .withClaim("userRole", appUser.getRole())
                    .sign(algorithm);

        } catch (JWTCreationException exception) {
            System.out.println(exception);
        }
        Random random = new Random();
        JwtToken jwtToken = new JwtToken(random.nextLong(), token);
        tokens.add(jwtToken);
        return EntityModel.of(jwtToken);
    }

}


