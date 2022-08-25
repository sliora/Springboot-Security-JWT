package com.cos.jwt.jwt;


import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//스프링 시큐리티에서 UsernamePasswordAuthenticationFilter가 있음
//lgoin 요청해서 username, password 전송하면(post)
//UsernamePasswordAuthenticationFilter가 동작을함
//config에 formLogin().disable()을 해서 작동을 안함
//그래서 .addFilter로 달아줄 예정
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;  //Manager를 사용할 수 있음

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        System.out.println("=========== 로그인 시도중 ============");

        //1. username, password를 받아서

        //2. 정상인지 로그인 시도

        //3. PrincipalDetail를 세션에 담아야 함 왜 담냐면 권한 관리가 됨.. 만약에 권한 관리가 필요 없으면 안해도 됨

        //4. JWT 토큰을 만들어서 응답

        return super.attemptAuthentication(request, response);
    }
}
