package com.feast.demo.filters;

import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by ggke on 2017/8/27.
 */
public class ParameterDecodeFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        for(String key:request.getParameterMap().keySet()){
            System.out.println(key);
        }
        System.out.println("ParameterDecodeFilter filter");
        filterChain.doFilter(request,response);
    }
}
