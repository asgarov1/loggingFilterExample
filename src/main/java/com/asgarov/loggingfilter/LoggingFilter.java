package com.asgarov.loggingfilter;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component
public class LoggingFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        var cachedRequest = new CachedBodyHttpServletRequest(request);
        log.info("PROCESSING REQUEST: " + request.getMethod() + " " + request.getRequestURI()
                + getParameters(request));
        filterChain.doFilter(cachedRequest, response);
    }

    @SneakyThrows
    private String getParameters(HttpServletRequest request) {
        String body = new String(request.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
        return body.isEmpty() ? "" : ", with following body: " + body;
    }
}
