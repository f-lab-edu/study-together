package dev.flab.studytogether.response;

import org.springframework.http.HttpStatus;

public class CommonResponse {
    boolean success;
    HttpStatus httpStatus;
    int statusCode;
    String message;

    protected Boolean isSuccess(int statusCode) {
        if (statusCode >= 200 && statusCode < 300) {
            return true;
        }

        if (statusCode >= 400) {
            return false;
        }

        return null;
    }
}
