package org.unstoppable.montao.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.unstoppable.montao.exception.*;
import org.unstoppable.montao.exception.Error;

@RestControllerAdvice
public class ExceptionHandlerController {
    @ExceptionHandler(CommunityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Error communityNotFound(CommunityNotFoundException e) {
        return new Error(HttpStatus.NOT_FOUND.value(), e.getMessage());
    }

    @ExceptionHandler(RegistrationFormException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Error userFormFailure(RegistrationFormException e) {
        return new Error(HttpStatus.CONFLICT.value(), e.getMessage());
    }

    @ExceptionHandler(UserNotAuthorizedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public Error userNotAuthorized(UserNotAuthorizedException e) {
        return new Error(HttpStatus.METHOD_NOT_ALLOWED.value(), e.getMessage());
    }

    @ExceptionHandler(ChannelNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Error channelNotFound(ChannelNotFoundException e) {
        return new Error(HttpStatus.NOT_FOUND.value(), e.getMessage());
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Error userNotFound(UserNotFoundException e) {
        return new Error(HttpStatus.NOT_FOUND.value(), e.getMessage());
    }

    @ExceptionHandler(ChannelFormException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Error userFormFailure(ChannelFormException e) {
        return new Error(HttpStatus.CONFLICT.value(), e.getMessage());
    }
}
