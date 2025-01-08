package com.example.demo.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class LoggingInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 요청 로그 출력
        System.out.println("Request URL: " + request.getRequestURL());
        System.out.println("Request Method: " + request.getMethod());
        System.out.println("Request Headers: ");
        request.getHeaderNames().asIterator().forEachRemaining(header -> {
            System.out.println(header + ": " + request.getHeader(header));
        });
        return true; // 요청을 계속 처리하도록 허용
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // 응답 로그 출력 (모든 요청 처리 후)
        System.out.println("Response Status: " + response.getStatus());
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 요청과 응답 처리 후 (뷰 렌더링 후)
        System.out.println("Request and Response complete.");
    }
}
