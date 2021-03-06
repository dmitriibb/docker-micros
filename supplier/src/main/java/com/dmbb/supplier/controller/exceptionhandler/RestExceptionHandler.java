package com.dmbb.supplier.controller.exceptionhandler;

import com.dmbb.supplier.model.dto.ErrorDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class RestExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorDTO handleAllExceptions(Exception e) {
        log.error(e.getMessage(), e);
        return new ErrorDTO(e.getMessage(), ExceptionUtils.getStackTrace(e));
    }

}
