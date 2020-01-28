package com.rest.recruit.util;

import io.jsonwebtoken.Jws;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import java.security.Key;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Value;
import io.jsonwebtoken.security.Keys;
import javax.annotation.PostConstruct;

@Component
public class JwtUtil {


    final String secret = "12345678901234567890123456789012";
    private Key key;


    public Claims getClaims(String token) {


        this.key = Keys.hmacShaKeyFor(secret.getBytes());

        return Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody();
    }


    public int getAuthentication(String token) {

        Claims claims = getClaims(token);

//        System.out.print("\nroletest\n");
  //      System.out.print(claims.get("roles"));
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
