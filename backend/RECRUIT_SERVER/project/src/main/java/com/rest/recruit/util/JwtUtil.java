package com.rest.recruit.util;
import io.jsonwebtoken.io.Decoders;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import io.jsonwebtoken.security.Keys;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.*;
import org.springframework.beans.factory.annotation.*;
import java.util.Base64;
import static java.nio.charset.StandardCharsets.UTF_8;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

@Component
public class JwtUtil {

//    final String secret = "12345678901234567890123456789012";

    @Value("${jwt.secret}")
    private String secret;
    private Key key;

    @PostConstruct
    public void init(){
        System.out.print("secret\n");
        System.out.print(secret);
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }


    public Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(key)
                .base64UrlDecodeWith(Decoders.BASE64)
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


    public String getToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        return (token==null)? null : token.substring("Bearer ".length());
    }


    public boolean isAccessToken(String token) {//ok
        return getClaims(token).get("tokenType").equals("ACCESS_TOKEN");
    }
}
