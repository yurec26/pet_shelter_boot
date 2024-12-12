package com.example.pet_shelter_boot.handler;

import com.example.pet_shelter_boot.dto.ErrorMassageResponseDTO;
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

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ErrorMassageResponseDTO> handleNoFoundException(
            NoSuchElementException e
    ) {
        ErrorMassageResponseDTO error = new ErrorMassageResponseDTO(
                "Такая сущность не найдена",
                e.getMessage(),
                LocalDateTime.now()
        );
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(error);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorMassageResponseDTO> handleServerException(
            RuntimeException e
    ) {
        ErrorMassageResponseDTO error = new ErrorMassageResponseDTO(
                "Ошибка на сервере",
                e.getMessage(),
                LocalDateTime.now()
        );
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(error);
    }
}
