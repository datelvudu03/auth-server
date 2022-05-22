package com.authserver.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.authserver.controller.controllerAdvice.exceptionHandler.AppUserIdNotFoundException;
import com.authserver.entity.AppUser;
import com.authserver.entity.JwtToken;
import com.authserver.controller.controllerAdvice.exceptionHandler.AppUserNotFoundException;
import com.authserver.repository.AppUserRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.http.ResponseEntity.notFound;


@RestController
@RequestMapping("api/users")

public class AppUserController {
    private final AppUserRepository appUserRepository;
    Random random = new Random();

    public AppUserController(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }
    @GetMapping("/findOneWithUsername/{username}")
    ResponseEntity<EntityModel<AppUser>> getUser(@PathVariable String username){
        return ResponseEntity.ok(
                EntityModel.of(appUserRepository.findByUsername(username),
                linkTo(methodOn(AppUserController.class).getUser(username)).withSelfRel()));
    }
    @GetMapping("/findOne/{id}")
    ResponseEntity<EntityModel<AppUser>> findOne(@PathVariable long id) {
        return appUserRepository.findById(id) //
                .map(appUser -> EntityModel.of(appUser, //
                        linkTo(methodOn(AppUserController.class).findOne(appUser.getId())).withSelfRel(), //
                        linkTo(methodOn(AppUserController.class).findAll()).withRel("appUsers"))) //
                .map(ResponseEntity::ok) //
                .orElse(notFound().build());
    }
    @GetMapping("/findAll")
    ResponseEntity<CollectionModel<EntityModel<AppUser>>> findAll(){
    List<EntityModel<AppUser>> appUsers = appUserRepository.findAll().stream()
            .map(appUser -> EntityModel.of(appUser, //
                    linkTo(methodOn(AppUserController.class).findOne(appUser.getId())).withSelfRel(),
                    //linkTo(methodOn(AppUserController.class).updateAppUser(appUser,appUser.getId())).withRel("editAppUser"),//
                    linkTo(methodOn(AppUserController.class).findAll()).withRel("appUsers"))) //
            .collect(Collectors.toList());
    return ResponseEntity.ok(CollectionModel.of(appUsers,
            linkTo(methodOn(AppUserController.class).findAll()).withSelfRel()));
    }

    @PostMapping("/addAppUser")
    ResponseEntity<EntityModel<AppUser>> addAppUser(@RequestBody AppUser newAppUser){
        return ResponseEntity.ok(EntityModel.of(appUserRepository.save(newAppUser),
                linkTo(methodOn(AppUserController.class).addAppUser(newAppUser)).withSelfRel()));
    }
    @PutMapping("/updateUser/{id}")
    ResponseEntity<EntityModel<AppUser>> updateAppUser(@RequestBody AppUser updatedAppUser, @PathVariable Long id) throws AppUserIdNotFoundException  {
        if (!appUserRepository.existsById(id)){
            //return ResponseEntity.notFound().build();
            throw new AppUserIdNotFoundException(id);
        }
        AppUser appUser = appUserRepository.getById(id);
        appUser.setUsername(updatedAppUser.getUsername());
        appUser.setPassword(updatedAppUser.getPassword());
        appUser.setRole(updatedAppUser.getRole());
        return ResponseEntity.ok(EntityModel.of(appUserRepository.save(appUser),
                    linkTo(methodOn(AppUserController.class).updateAppUser(updatedAppUser,id)).withSelfRel()));

    }
    @DeleteMapping("/deleteAppUser/{id}")
    String deleteAppUser(@PathVariable Long id) {
        appUserRepository.deleteById(id);
        return "User with id "+ id +" deleted";
    }


    @GetMapping("/userGet/{username}/{password}")
    boolean getUser(@PathVariable String username,@PathVariable String password){
        return appUserRepository.existsByUsernameAndPassword(username,password);
    }

    @PostMapping("/userGetJwt/{username}/{password}")
    ResponseEntity<EntityModel<JwtToken>> getToken(@PathVariable String username,@PathVariable String password) throws AppUserNotFoundException{
        List<AppUser> appUsers = appUserRepository.findByUsernameAndPassword(username,password);
        if (appUsers.isEmpty()){
            throw new AppUserNotFoundException(username);
        }
        JwtToken jwtToken = new JwtToken();
        AppUser appUser = appUsers.get(0);
        System.out.println(appUser + "asd");
        String token;
        try {
            Algorithm algorithm = Algorithm.HMAC256("43f3b8fd2bab8815df2db818d08eeac0");
            token = JWT.create()
                    .withIssuer("datudy")
                    .withClaim("userId", appUser.getId())
                    .withClaim("userName", appUser.getUsername())
                    .withClaim("userRole", appUser.getRole())
                    .sign(algorithm);

            jwtToken.setId(random.nextLong());
            jwtToken.setToken(token);
        } catch (JWTCreationException exception) {
            System.out.println(exception);
        }



       /* try{
            Algorithm algorithm = Algorithm.HMAC256("43f3b8fd2bab8815df2db818d08eeac0");
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("datudy")
                    .build();
            DecodedJWT jwt = verifier.verify(token);
            System.out.println(jwt.getClaims());
        }catch (JWTDecodeException exception){
            System.out.println(exception);
        }*/
        return ResponseEntity.ok(EntityModel.of(jwtToken,
                linkTo(methodOn(AppUserController.class).getToken(username, password)).withSelfRel()));
    }

}


