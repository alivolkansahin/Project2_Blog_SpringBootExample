package com.volkans.avsblog.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.ConstraintViolationException;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AvsBlogException.class)
    @ResponseBody
    public ResponseEntity<ErrorMessage> handleManagerException(AvsBlogException ex){
        HttpStatus httpStatus= ex.getErrorType().getHttpStatus();
        return new ResponseEntity<>(createError(ex),httpStatus);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class) //5501   // @Valid ile sağlanmayan (bu kurguda @NotBlank) hataları yakalanıyor
    @ResponseBody
    public ResponseEntity<ErrorMessage> handleValidationException(MethodArgumentNotValidException ex){
        String customizedMessage = ex.getBindingResult().getFieldErrors().stream().map(error -> error.getDefaultMessage()).collect(Collectors.joining(","));
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST; // 400
        return new ResponseEntity<>(createError(ex, 5501, customizedMessage), httpStatus);
    }

    @ExceptionHandler(ConstraintViolationException.class) //5601    // Entity sınıfındaki hazır kütüphane annotationları ve custom annotationların hataları yakalanıyor.
    @ResponseBody
    public ResponseEntity<ErrorMessage> handleValidationException(ConstraintViolationException ex){
        String customizedMessage = ex.getConstraintViolations().stream().map(error -> error.getMessage()).collect(Collectors.joining(","));
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST; // 400 // 422 Unprocessable Entity ne kullanılabilir.
        return new ResponseEntity<>(createError(ex, 5601, customizedMessage), httpStatus);
    }

    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody
    public ResponseEntity<ErrorMessage> handleManagerException(ArithmeticException ex){
        HttpStatus httpStatus= HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(createError(ex, httpStatus.value()),httpStatus);
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<ErrorMessage> handleManagerException(Exception ex){
        HttpStatus httpStatus= HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(createError(ex, httpStatus.value()),httpStatus);
    }

    private ErrorMessage createError(AvsBlogException ex){
        return ErrorMessage.builder()
                .code(ex.getErrorType().getCode())
                .message(ex.getMessage())
                .build();
    }

    private ErrorMessage createError(Exception ex, int value){
        return ErrorMessage.builder()
                .code(value)
                .message(ex.getMessage())
                .build();
    }

    private ErrorMessage createError(Exception ex, int value, String customizedMessage){
        return ErrorMessage.builder()
                .code(value)
                .message(customizedMessage)
                .build();
    }

}
