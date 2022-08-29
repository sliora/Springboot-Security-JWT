package com.cos.jwt.jwt;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.cos.jwt.auth.PrincipalDetails;
import com.cos.jwt.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;

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
        try {
/*            BufferedReader reader = request.getReader();
            String input = null;
            while ((input = reader.readLine()) != null) {
                System.out.println("input = " + input);
            }*/

            ObjectMapper objectMapper = new ObjectMapper();
            User user = objectMapper.readValue(request.getInputStream(), User.class);
            System.out.println("user = " + user);

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());

            //PrincipalDetailsService의 loadUserByUsername() 함수가 실행됨
            //정상이면 authentication이 리턴 됨
            //DB에 있는 username과 password가 일치한다.
            Authentication authentication = authenticationManager.authenticate(authenticationToken);

            //PrincipalDetail를 세션에 담아야 함 왜 담냐면 권한 관리가 됨.. 만약에 권한 관리가 필요 없으면 안해도 됨
            PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
            System.out.println("principalDetails.getUser().getUsername() = " + principalDetails.getUser().getUsername()); //로그인 정상적으로 되었다는 뜻
            System.out.println("principalDetails.getUser().getUsername() = " + principalDetails.getUser().getPassword());

            //authentication 객체가 session 영역에 저장을 해야하고 그 방법이 return 해주면 됨
            //리턴의 이유는 권한 관리를 security 가 대신 해주기 때문에 편하려고 하는 것
            //굳이 JWT 토큰을 사용하면서 세션을 만들 이유가 없는 근데 단지 권한 처리 때문에 session 넣어줌

            return authentication;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    //attemptAuthentication 실행 후 인증이 정상적으로 되었으면 successfulAuthentication 함수가 실행됨
    //JWT 토큰을 만들어서 request 요청한 사용자에게 JWT 토큰을 response 해주면 됨
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        System.out.println("==========인증이 완료됨=======");

        PrincipalDetails principalDetails = (PrincipalDetails) authResult.getPrincipal();

        String jwtToken = JWT.create()
                .withSubject("cos토큰")
                .withExpiresAt(new Date(System.currentTimeMillis() + (60000 * 1)))
                .withClaim("id", principalDetails.getUser().getId())
                .withClaim("username", principalDetails.getUser().getUsername())
                .sign(Algorithm.HMAC512("cos"));

        response.addHeader("Authorization", "Bearer " + jwtToken);
    }
}
