package dev.flab.studytogether.handler;

import dev.flab.studytogether.exception.BadRequestException;
import dev.flab.studytogether.exception.NotFoundException;
import dev.flab.studytogether.response.ApiResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(BadRequestException.class)
    public ApiResponse<Void> badRequestExceptionHandle(BadRequestException e) {
        return ApiResponse.badRequest(e.getMessage());
    }

    @ExceptionHandler(NotFoundException.class)
    public ApiResponse<Void> notFoundExceptionHandle(NotFoundException e) {
        return ApiResponse.notFound(e.getMessage());
    }
}
