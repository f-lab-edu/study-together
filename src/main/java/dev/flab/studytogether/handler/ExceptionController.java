package dev.flab.studytogether.handler;

import dev.flab.studytogether.exception.BadRequestException;
import dev.flab.studytogether.exception.NotFoundException;
import dev.flab.studytogether.response.SingleApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadRequestException.class)
    public SingleApiResponse<Void> badRequestExceptionHandle(BadRequestException e) {
        return SingleApiResponse.badRequest(e.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public SingleApiResponse<Void> notFoundExceptionHandle(NotFoundException e) {
        return SingleApiResponse.notFound(e.getMessage());
    }
}
