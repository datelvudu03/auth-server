package com.authserver.controller.controllerAdvice;

import com.authserver.controller.controllerAdvice.exceptionHandler.AppUserIdNotFoundException;
import com.authserver.controller.controllerAdvice.exceptionHandler.AppUserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class ControllerAdvisor {
    @ResponseBody
    @ExceptionHandler(AppUserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String appUserUsernameNotFoundHandler(AppUserNotFoundException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(AppUserIdNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String appUserIdNotFoundHandler(AppUserIdNotFoundException ex) {
        return ex.getMessage();
    }
}
