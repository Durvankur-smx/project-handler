package com.samyak.projecttool.exception.handler;

import com.samyak.projecttool.exception.AccessDeniedException;
import com.samyak.projecttool.exception.ResourceNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(basePackages = "com.samyak.projecttool.controller.page")
public class UiExceptionHandler {

    @ExceptionHandler(AccessDeniedException.class)
    public String accessDenied() {
        return "error/403";
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public String notFound() {
        return "error/404";
    }

    @ExceptionHandler(Exception.class)
    public String genericError() {
        return "error/500";
    }
}
