package com.authserver.exceptionHandler;

public class AppUserNotFoundException extends RuntimeException {

    public AppUserNotFoundException(String username) {
        super("Could not find user " + username);
    }
}
