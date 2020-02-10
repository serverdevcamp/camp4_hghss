package com.smilegate.resume.interceptor;

import com.smilegate.resume.exceptions.InvalidTokenException;
import com.smilegate.resume.exceptions.TokenNotExistException;
import com.smilegate.resume.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
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
        if(token == null) return true;
        if(token.equals("Bearer")) throw new TokenNotExistException();
        if(!(jwtUtil.isValidToken(token) && jwtUtil.isAccessToken(token))) {
            throw new InvalidTokenException();
        }
        return true;
    }

}
