package com.example.demo.jwt;

import com.example.demo.member.dto.CustomUserDetails;
import com.example.demo.member.entity.Member;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;

    public JWTFilter(JWTUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println(request.getHeaderNames());
        //request에서 Authorization 헤더를 찾음
        String authorization = request.getHeader("Authorization");
        System.out.println("토큰을 확인해보자!!"+authorization);
        if (authorization == null || !authorization.startsWith("Bearer ")) {

            System.out.println("token null");
            filterChain.doFilter(request, response);
            
            //조건이 해당되면 메소드 종료 (필수)
            return;
        }
        //Bearer 부분 제거 후 순수 토큰만 획득
        String token = authorization.split(" ")[1];
        
        //토큰 소멸 시간 검증
        if (jwtUtil.isExpired(token)) {
            
            System.out.println("token expired");
            filterChain.doFilter(request, response);
            
            //조건이 해당되면 메소드 종료 (필수)
            return;
        }

        //토큰에서 userId와 role 획득
        String userId = jwtUtil.getUserid(token);
        String role = jwtUtil.getRole(token);

        //member 엔티티를 생성하여 값 set
        CustomUserDetails customUserDetails = new CustomUserDetails(
                Member.builder()
                        .memberId(userId)
                        .password("temppassword")
                        .role(role)
                        .build()
        );

        //스프링 시큐리티 인증 토큰 생성
        Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
        //세션에 사용자 등록
        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);

    }
}
