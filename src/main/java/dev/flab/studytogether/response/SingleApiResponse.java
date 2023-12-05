package dev.flab.studytogether.response;

import org.springframework.http.HttpStatus;

public class SingleApiResponse<T> extends CommonResponse{
    T data;

    private SingleApiResponse(HttpStatus httpStatus, String message, T data) {
        this.success = isSuccess(httpStatus.value());
        this.statusCode = httpStatus.value();
        this.httpStatus = httpStatus;
        this.message = message;
        this.data = data;
    }

    public static <T> SingleApiResponse<T> of(HttpStatus httpStatus, String message, T data) {
        return new SingleApiResponse<>(httpStatus, message, data);
    }

    public static <T> SingleApiResponse<T> ok(T data) {
        return of(HttpStatus.OK, HttpStatus.OK.name(), data);
    }
}
