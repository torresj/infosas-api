package com.torresj.infosas.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(StaffNotFoundException.class)
    ProblemDetail handleStaffNotFound(StaffNotFoundException e) {
        ProblemDetail problemDetail =
                ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());
        problemDetail.setTitle(e.getMessage());
        log.error(e.toString());
        return problemDetail;
    }

    @ExceptionHandler(BadRequestException.class)
    ProblemDetail handleBadRequestException(BadRequestException e) {
        ProblemDetail problemDetail =
                ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage());
        problemDetail.setTitle(e.getMessage());
        log.error(e.toString());
        return problemDetail;
    }

    @ExceptionHandler(InfoSasAuthenticationException.class)
    ProblemDetail handleInfoSasAuthenticationException(InfoSasAuthenticationException e) {
        ProblemDetail problemDetail =
                ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, e.getMessage());
        problemDetail.setTitle(e.getMessage());
        log.error(e.toString());
        return problemDetail;
    }

    @ExceptionHandler(ClientNotFoundException.class)
    ProblemDetail handleClientNotFoundExceptionException(ClientNotFoundException e) {
        ProblemDetail problemDetail =
                ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());
        problemDetail.setTitle(e.getMessage());
        log.error(e.toString());
        return problemDetail;
    }
}
