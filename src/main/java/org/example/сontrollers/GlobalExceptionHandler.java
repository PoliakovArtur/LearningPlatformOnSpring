package org.example.сontrollers;

import org.example.dto.MessageDTO;
import org.example.exceptions.BadRequestException;
import org.example.exceptions.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({NoHandlerFoundException.class, NotFoundException.class})
    public ResponseEntity<MessageDTO> notFound(Exception ex) {

        String message =
                ex instanceof NoHandlerFoundException
                ? "Ресурс не найден"
                        : ex.getMessage();
        return new ResponseEntity<>(
                new MessageDTO(message), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({
            HttpMessageNotReadableException.class,
            BadRequestException.class,
            MethodArgumentTypeMismatchException.class
    })
    public ResponseEntity<MessageDTO> badRequest(Exception ex) {

        String message =
                ex instanceof HttpMessageNotReadableException
                ? "Недопустимый формат тела запроса" : (
                        ex instanceof MethodArgumentTypeMismatchException
                                ? "Недопустимое значение id" :
                                ex.getMessage());

        return new ResponseEntity<>(new MessageDTO(message), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<MessageDTO> internalServerError() {

        return new ResponseEntity<>(
                new MessageDTO("Сбой в работе сервера"), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
