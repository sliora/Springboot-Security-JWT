package com.cos.jwt.Filter;


import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class MyFilter1 implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {


        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        if (req.getMethod().equals("POST")) {
            String headerAuth = req.getHeader("Authorization");

            if (headerAuth.equals("cos")) {
                System.out.println("인증됨");
                chain.doFilter(req, res); //프로세스를 계속 진행해주기 위해서는 체인에 걸어줘야함 안그러면 걍 프로그램이 끝나버림..
            } else {
                PrintWriter writer = res.getWriter();
                writer.println("인증안됨");
            }
            System.out.println("headerAuth = " + headerAuth);
        }
        else {
            System.out.println(" ====== 필터에 걸리심 ㅋ======= ");
        }
    }
}
