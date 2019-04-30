package com.example.security.filte;

import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

/**
 * @author shiyuan
 * @create 2019/4/30
 */
public class BeforeLoginFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("this is a filter before UsernamePasswordAuthenticationFilter.");
        //继续调用Filter链
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
