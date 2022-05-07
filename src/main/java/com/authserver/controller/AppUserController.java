package com.authserver.controller;

import com.authserver.entity.AppUser;
import com.authserver.repository.AppUserRepository;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/users")

public class AppUserController {
    private final AppUserRepository appUserRepository;

    public AppUserController(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    @GetMapping("/login/{username}/{password}")
    EntityModel<AppUser> login(@PathVariable String username,@PathVariable String password){
        AppUser appUser = appUserRepository.findBy(username);
    }
}
