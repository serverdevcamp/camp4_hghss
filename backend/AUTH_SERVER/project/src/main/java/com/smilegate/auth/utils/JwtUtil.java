package com.smilegate.auth.utils;

import com.smilegate.auth.domain.CustomUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Date;
import java.util.List;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;
    private Key key;

    private final long MINUTE = 1000L * 60;
//
//    private final UserDetailsService userDetailsService;
//
//    public JwtUtil(@Qualifier("customUserDetailsService") UserDetailsService userDetailsService) {
//        this.userDetailsService = userDetailsService;
//    }

    @PostConstruct
    protected void init() {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String createToken(Integer userId, String email, String nickname, List<String> roles, String tokenType, int minutes) {
        Claims claims = Jwts.claims().setSubject(email);
        claims.put("userId", userId);
        claims.put("email", email);
        claims.put("nickname", nickname);
        claims.put("roles", roles);
        claims.put("tokenType", tokenType);

        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(new Date(now.getTime() + minutes*MINUTE))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public Authentication getAuthentication(String token) {
        Claims claims = getClaims(token);
        String email = claims.getSubject();
        String grade = (String) ((List) claims.get("roles")).get(0);

        UserDetails userDetails = CustomUserDetails.builder().email(email).role(grade).build();
        //UserDetails userDetails = userDetailsService.loadUserByUsername(getClaims(token).getSubject());

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
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
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(key).parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isAccessToken(String token) {
        return getClaims(token).get("tokenType").equals("ACCESS_TOKEN");
    }

    public boolean isRefreshToken(String token) {
        return getClaims(token).get("tokenType").equals("REFRESH_TOKEN");
    }

}
