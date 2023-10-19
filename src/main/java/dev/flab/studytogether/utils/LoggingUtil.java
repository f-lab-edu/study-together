package dev.flab.studytogether.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.util.ContentCachingRequestWrapper;
import java.util.*;

public class LoggingUtil {
    private LoggingUtil() {} //인스턴스화 방지

    public static String makeLoggingRequestJSON(ContentCachingRequestWrapper request) throws JsonProcessingException {
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("uri", request.getRequestURI());
        requestMap.put("method", request.getMethod());

        Enumeration<String> list = request.getParameterNames();
        Map<String, String> bodyMap = new HashMap<>();

        while(list.hasMoreElements()){
            String parameter = list.nextElement();
            bodyMap.put(parameter, request.getParameter(parameter));
        }
        requestMap.put("body", bodyMap);
        ObjectMapper objectMapper = new ObjectMapper();

        return objectMapper.writeValueAsString(requestMap);
    }


}
