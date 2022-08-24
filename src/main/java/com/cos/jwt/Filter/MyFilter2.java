package com.cos.jwt.Filter;


import javax.servlet.*;
import java.io.IOException;

public class MyFilter2 implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("필터2");
        chain.doFilter(request, response); //프로세스를 계속 진행해주기 위해서는 체인에 걸어줘야함 안그러면 걍 프로그램이 끝나버림..
    }
}
