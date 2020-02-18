package com.rest.recruit.util;

import com.rest.recruit.controller.RecruitController;
import io.jsonwebtoken.Jws;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import java.security.Key;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import io.jsonwebtoken.security.Keys;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

@Component
public class JwtUtil {

//    final String secret = "12345678901234567890123456789012";

    @Value("${jwt.secret}")
    private String secret;
    private Key key;
    private static final Logger logger = LoggerFactory.getLogger(RecruitController.class);
    @PostConstruct
    public void init(){
        System.out.print("secret\n");
        System.out.print(secret);
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }


    public Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody();
    }


    public int getAuthentication(String token) {

        Claims claims = getClaims(token);

        System.out.print("\ntoken\n");
        System.out.print(token);
        System.out.print("\nuserId\n");
              System.out.print(claims.get("userId")); //대문자로 access_token / refresh_token check!!!!!! // role index로 바꿈!!!!!!!!
/*
        String email = claims.getSubject();
        String grade = (String) ((List) claims.get("roles")).get(0);
        String userIdx = (String) ((List) claims.get("userId")).get(0);
*/
        return (Integer) claims.get("userId");
    }

    public boolean isValidToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(key).parseClaimsJws(token);

            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }


    public String getToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        return (token==null)? null : token.substring("Bearer ".length());
    }


    public boolean isAccessToken(String token) {
        return getClaims(token).get("tokenType").equals("ACCESS_TOKEN");
    }
}
