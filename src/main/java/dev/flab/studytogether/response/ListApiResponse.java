package dev.flab.studytogether.response;

import org.springframework.http.HttpStatus;

import java.util.List;

public class ListApiResponse<T> extends CommonResponse {
    List<T> data;

    private ListApiResponse(HttpStatus httpStatus, List<T> data) {
        this.success = isSuccess(httpStatus.value());
        this.statusCode = httpStatus.value();
        this.httpStatus = httpStatus;
        this.data = data;
    }

    public static <T> ListApiResponse<T> of(HttpStatus httpStatus, List<T> data) {
        return new ListApiResponse<>(httpStatus, data);
    }

    public static <T> ListApiResponse<T> ok(List<T> data) {
        return of(HttpStatus.OK, data);
    }
}
