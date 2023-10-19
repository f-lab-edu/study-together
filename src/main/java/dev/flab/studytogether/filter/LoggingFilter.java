package dev.flab.studytogether.filter;



import dev.flab.studytogether.utils.LoggingUtil;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@WebFilter("/*")
public class LoggingFilter implements Filter {

    private final org.slf4j.Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        //request, response 캐시 가능하도록 wrapping
        ContentCachingRequestWrapper httpServletRequest =
                new ContentCachingRequestWrapper((HttpServletRequest) request);
        ContentCachingResponseWrapper httpServletResponse =
                new ContentCachingResponseWrapper((HttpServletResponse) response);

        chain.doFilter(httpServletRequest, httpServletResponse);

        String reqContentJSon = LoggingUtil.makeLoggingRequestJSON(httpServletRequest);
        String resContent = new String(httpServletResponse.getContentAsByteArray());
        
        log.info("request : {}", reqContentJSon);
        //response 추후 수정 예정
        log.info("response : {}", resContent);

    }
}

