package com.kg.kg.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@WebFilter("/Institutions/*")
public class INRequestFilter implements Filter{
    private static final Logger logger = Logger.getLogger(INRequestFilter.class.getName());

    @Override
    public void init(FilterConfig filterConfig) {
        logger.info("Initializing RequestLoggingFilter");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        long startTime = System.currentTimeMillis();

        // Log the HTTP request details
        logRequestDetails(httpRequest);

        try {
            chain.doFilter(request, response);
        } finally {
            long duration = System.currentTimeMillis() - startTime;
            logger.info("Request to [" + httpRequest.getRequestURI() + "] completed in " + duration + " ms");
            System.out.println(duration);
        }
    }

    private void logRequestDetails(HttpServletRequest request) {
        Map<String, String[]> paramMap = request.getParameterMap();
        String params = paramMap.entrySet().stream()
                .map(entry -> entry.getKey() + "=" + String.join(",", entry.getValue()))
                .collect(Collectors.joining(", ", "{", "}"));

        logger.info("Request URI: " + request.getRequestURI());
        logger.info("Request Method: " + request.getMethod());
        logger.info("Query String: " + request.getQueryString());
        logger.info("Request Parameters: " + params);
    }

    @Override
    public void destroy() {
        logger.info("Destroying RequestLoggingFilter");
    }
}
