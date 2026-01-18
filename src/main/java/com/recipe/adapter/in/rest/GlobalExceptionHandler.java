package com.recipe.adapter.in.rest;

import com.recipe.api.model.ErrorResponse;
import com.recipe.domain.core.RecipeException;
import com.recipe.domain.core.RecipeNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Optional;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({RecipeNotFoundException.class})
    ResponseEntity<Object> handleRecipeNotFoundException(RecipeNotFoundException ex, WebRequest request) {

        String message = Optional
                .ofNullable(ex)
                .map(Throwable::getMessage)
                .filter(StringUtils::hasText)
                .orElse("recipe not found");
        ErrorResponse errorResponse = ErrorResponse
                .builder()
                .error(message)
                .build();
        return super.handleExceptionInternal(ex, errorResponse, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler({RecipeException.class})
    ResponseEntity<Object> handleRecipeException(RecipeException ex, WebRequest request) {

        String message = Optional
                .ofNullable(ex)
                .map(Throwable::getMessage)
                .filter(StringUtils::hasText)
                .orElse("internal server error");
        ErrorResponse errorResponse = ErrorResponse
                .builder()
                .error(message)
                .build();
        return super.handleExceptionInternal(ex, errorResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }
}