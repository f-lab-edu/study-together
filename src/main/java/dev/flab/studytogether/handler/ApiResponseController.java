package dev.flab.studytogether.handler;

import dev.flab.studytogether.response.ApiResponse;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;


@RestControllerAdvice
public class ApiResponseController implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        ResponseStatus responseStatus = returnType.getMethodAnnotation(ResponseStatus.class);

        return responseStatus != null &&
                responseStatus.value().is2xxSuccessful() &&
                returnType.getParameterType() != String.class &&
                MappingJackson2MessageConverter.class.isAssignableFrom(converterType);
    }

    @Override
    public Object beforeBodyWrite(Object body,
                                  MethodParameter returnType,
                                  MediaType selectedContentType,
                                  Class selectedConverterType,
                                  ServerHttpRequest request,
                                  ServerHttpResponse response) {

        return ApiResponse.ok(body);
    }
}
