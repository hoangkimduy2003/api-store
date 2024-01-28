package com.duyhk.clothing_ecommerce.exception;

import com.duyhk.clothing_ecommerce.dto.ResponseDTO;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.PropertyNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
@Slf4j
public class ExceptionController extends RuntimeException {
    @ExceptionHandler(CustomValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseDTO<Void> handleCustomValidationException(CustomValidationException  e) {
        e.printStackTrace();
        return ResponseDTO.<Void>builder()
                .status(400)
                .msg(e.getMessage())
                .build();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseDTO<Void> notFound(IllegalArgumentException e) {

        return ResponseDTO.<Void>builder()
                .status(404)
                .msg(e.getMessage())
                .build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseDTO<Void> validateFail(MethodArgumentNotValidException e) {
        e.printStackTrace();
        return ResponseDTO.<Void>builder()
                .status(400)
                .msg(e.getBindingResult().getFieldError().getDefaultMessage())
                .build();
    }

    @ExceptionHandler(PropertyNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseDTO<Void> notFound(PropertyNotFoundException e) {
        e.printStackTrace();
        return ResponseDTO.<Void>builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .msg(e.getMessage())
                .build();
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseDTO<Void> notFound(EntityNotFoundException e) {
        e.printStackTrace();
        return ResponseDTO.<Void>builder()
                .msg(e.getMessage())
                .status(404)
                .build();
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseDTO<Void> notFound(HttpMessageNotReadableException e) {
        e.printStackTrace();
        return ResponseDTO.<Void>builder()
                .msg(e.getMessage())
                .status(400)
                .build();
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseDTO<Void> badCredentialsException(BadCredentialsException e){
        e.printStackTrace();
        return ResponseDTO.<Void>builder()
                .status(403)
                .msg("Số điện thoại hoặc mật khẩu không chính xác")
                .build();
    }

    @ExceptionHandler(ExpiredJwtException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseDTO<Void> expiredJwtException(ExpiredJwtException e){
        e.printStackTrace();
        return ResponseDTO.<Void>builder()
                .status(403)
                .msg(e.getMessage())
                .build();
    }
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseDTO<Void> expiredJwtException(AccessDeniedException e){
        e.printStackTrace();
        return ResponseDTO.<Void>builder()
                .status(403)
                .msg(e.getMessage())
                .build();
    }

    @ExceptionHandler(Exception.class)
    public ResponseDTO<Void> exception(Exception e){
        e.printStackTrace();
        return ResponseDTO.<Void>builder()
                .msg(e.getMessage())
                .build();
    }

}
