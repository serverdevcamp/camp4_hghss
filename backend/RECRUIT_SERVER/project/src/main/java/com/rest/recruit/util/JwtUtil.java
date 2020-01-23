package com.rest.recruit.util;

import io.jsonwebtoken.Jws;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import java.security.Key;
import java.util.Date;
import java.util.List;

@Component
public class JwtUtil {

    private Key key;

    public Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody();
    }


    public String getAuthentication(String token) {
        Claims claims = getClaims(token);
        System.out.print("idtest\n");
        claims.getId();

        System.out.print("roletest\n");
        System.out.print(claims.get("roles"));

        String email = claims.getSubject();
        String grade = (String) ((List) claims.get("roles")).get(0);
        String userIdx = (String) ((List) claims.get("userId")).get(0);
        return userIdx;
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
