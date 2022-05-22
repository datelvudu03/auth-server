package com.authserver.controller.controllerAdvice.exceptionHandler;

public class AppUserIdNotFoundException extends RuntimeException{

    public AppUserIdNotFoundException(Long id) {
        super("Could not find user with this id:  " + id);
    }
}
