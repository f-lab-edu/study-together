package dev.flab.studytogether.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginCheckInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String requestURI = request.getRequestURI();
        System.out.println("[interceptor] : " + requestURI);

        HttpSession httpSession = request.getSession(false);

        if(httpSession == null || httpSession.getAttribute("seq_id") == null){
            System.out.println("[미인증 사용자 요청]");
            response.sendRedirect("/login");

            return  false;
        }


        return true;
    }
}
