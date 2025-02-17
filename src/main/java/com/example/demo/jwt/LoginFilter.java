package com.example.demo.jwt;

import com.example.demo.jwt.entity.RefreshToken;
import com.example.demo.jwt.repository.RefreshTokenRepository;
import com.example.demo.member.dto.CustomUserDetails;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.UUID;

public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;
    private final RefreshTokenRepository refreshTokenRepository;

    public LoginFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil, RefreshTokenRepository refreshTokenRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        //클라이언트 요청 id, passwd 추출
        String memberId = obtainUsername(request);
        String passWord = obtainPassword(request);
        System.out.println(memberId + " " + passWord);

        //스프링 시큐리티에서 ID와 passwd를 검증하기 위해 토큰에 담아야함
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(memberId, passWord, null);

        //AuthenticationManager로 생성한 토큰 전달
        return authenticationManager.authenticate(authToken);
    }

    //로그인 성공시 실행하는 메소드(JWT 발급하는곳)
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();

        String memberId = customUserDetails.getUsername();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();

        String role = auth.getAuthority();
        //refreshToken redis에 저장
        String refreshToken = UUID.randomUUID().toString();
        refreshTokenRepository.findById(memberId).ifPresent(refreshTokenRepository::delete);
        refreshTokenRepository.save(new RefreshToken(refreshToken, memberId));
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        // 객체를 JSON으로 변환
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonResponse = objectMapper.writeValueAsString(new RefreshToken(memberId, refreshToken));

        // JSON 데이터 응답 본문에 쓰기
        response.getWriter().write(jsonResponse);
        
        //accessToken 전송
        String accessToken = jwtUtil.createJwt(memberId, role, 60*60*10L);
        response.addHeader("Authorization", "Bearer " + accessToken);
        response.getWriter().flush(); //
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        response.setStatus(401);
    }
}
