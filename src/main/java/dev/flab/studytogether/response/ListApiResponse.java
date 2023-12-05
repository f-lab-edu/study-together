package dev.flab.studytogether.response;

import java.util.List;

public class ListApiResponse<T> extends CommonResponse {
    List<T> data;
}
