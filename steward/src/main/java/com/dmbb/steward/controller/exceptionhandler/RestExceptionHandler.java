package com.dmbb.steward.controller.exceptionhandler;

import com.dmbb.steward.model.dto.ErrorDTO;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorDTO handleAllExceptions(Exception e) {
        return new ErrorDTO(e.getMessage(), ExceptionUtils.getStackTrace(e));
    }

}
