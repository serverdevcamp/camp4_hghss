package com.smilegate.resume.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;
    private Key key;

    @PostConstruct
    protected void JwtUtil() {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    public Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody();
    }

    public String getToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        return (token==null)? null : token.substring("Bearer ".length());
    }

    public boolean isValidToken(String token) {
        Jws<Claims> claims = null;
        try{
            claims = Jwts.parser().setSigningKey(key).parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        }catch (Exception e) {
//            System.out.println("JWT Util ==============================");
//            System.out.println(e.getClass());
//            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean isAccessToken(String token) {
        return getClaims(token).get("tokenType").equals("ACCESS_TOKEN");
    }

}