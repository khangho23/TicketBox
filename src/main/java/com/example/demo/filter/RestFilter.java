package com.example.demo.filter;

import java.io.IOException;
import java.util.Enumeration;

import org.springframework.web.filter.GenericFilterBean;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class RestFilter extends GenericFilterBean {

    final String KEY_FILTER = "abc123456";

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        Enumeration<String> headerNames = httpRequest.getHeaders("Zuhot-Key");
        System.out.println(httpRequest.getRequestURI());
        if (httpRequest.getRequestURI().contains("/api")
                && (headerNames.hasMoreElements() && !headerNames.nextElement().equals(KEY_FILTER))) {
            // httpResponse.resetBuffer();
            httpResponse.sendError(404, "Not found");
            // httpResponse.setHeader("Content-Type", "application/json");
            // httpResponse.getOutputStream().print("{\"errorMessage\":\"Key Request invalid!\"}");
            // httpResponse.flushBuffer();
        }
        chain.doFilter(httpRequest, httpResponse);
    }
}
