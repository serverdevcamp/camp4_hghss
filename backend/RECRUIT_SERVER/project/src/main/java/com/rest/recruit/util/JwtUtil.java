package com.rest.recruit.util;

import io.jsonwebtoken.Jws;
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

@Component
public class JwtUtil {

//    final String secret = "12345678901234567890123456789012";

    @Value("${jwt.secret}")
    private String secret;
    private Key key;

    @PostConstruct
    public void init(){
        System.out.print("sls\n");
        System.out.print(secret);
    }


    public Claims getClaims(String token) {

        this.key = Keys.hmacShaKeyFor(secret.getBytes());


        return Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody();
    }


    public int getAuthentication(String token) {

        Claims claims = getClaims(token);

//        System.out.print("\nroletest\n");꿈
  //      System.out.print(claims.get("tokenType")); //대문자로 access_token / refresh_token check!!!!!! // role index로 바꿈!!!!!!!!
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


}
