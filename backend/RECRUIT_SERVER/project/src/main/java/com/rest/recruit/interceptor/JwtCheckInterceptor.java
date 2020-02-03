package com.rest.recruit.interceptor;

import com.rest.recruit.exception.ExpiredTokenException;
import com.rest.recruit.exception.UnauthorizedException;
import com.rest.recruit.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class JwtCheckInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = jwtUtil.getToken(request);

        if (token == null || !jwtUtil.isAccessToken(token)) {
            throw new UnauthorizedException();
        }

        if (!jwtUtil.isValidToken(token)) {
            throw new ExpiredTokenException();
        }

        return true;
    }

}