package com.example.pet_shelter_boot.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ExceptionHandlers {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMassageResponseDTO> handleValidationException(
            MethodArgumentNotValidException e
    ) {
        String detailedMessage = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(er -> er.getField() + ": " + er.getDefaultMessage())
                .collect(Collectors.joining(", "));

        ErrorMassageResponseDTO error = new ErrorMassageResponseDTO(
                "Ошибка валидации запроса",
                detailedMessage,
                LocalDateTime.now()
        );
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(error);
    }


    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorMassageResponseDTO> handleServerException(
            RuntimeException e
    ) {
        ErrorMassageResponseDTO error = new ErrorMassageResponseDTO(
                "Ошибка на сервере",
                "Обратитесь в службу поддержки сервиса",
                LocalDateTime.now()
        );
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(error);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorMassageResponseDTO> handleEntityNotFoundException(
            EntityNotFoundException e
    ) {
        ErrorMassageResponseDTO error = new ErrorMassageResponseDTO(
                "Такая сущность не найдена",
                e.getMessage(),
                LocalDateTime.now()
        );
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(error);
    }

    @ExceptionHandler({DataIntegrityViolationException.class,
            IllegalArgumentException.class})
    public ResponseEntity<ErrorMassageResponseDTO> handleIntegrityException(
            Exception e
    ) {
        ErrorMassageResponseDTO error = new ErrorMassageResponseDTO(
                "Запрос неправильный",
                e.getMessage(),
                LocalDateTime.now()
        );
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(error);
    }
}
