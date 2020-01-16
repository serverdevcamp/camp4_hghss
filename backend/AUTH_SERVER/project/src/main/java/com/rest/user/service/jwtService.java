package com.rest.user.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.rest.user.config.jwt;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.auth0.jwt.JWT.require;

@Slf4j
@Service
public class jwtService {

    private String ISSUER = jwt.ISSUER;

    private String SECRET = jwt.SECRET;

    //header+'.'+payload(Claims)+'.'+secret 으로 전송.

    //header = token의 type인 jwt와 서명에 들어가는 알고리즘 hmac256과 같 은것이 들어감.
    // 이 json이 base 64 로 인코딩되어 header에 들어감.

    //signature = hmacsha256(base64UrlEncode(header) + ".".base64UrlEncode(payload),secret)
    public String create(final int idx,final String grade,final String token) {

        try {
            //토큰 생성 빌더 객체 생성
            //토큰 생성자 명시
            JWTCreator.Builder b = JWT.create();

            //header명시
            Map<String,Object> header = new HashMap<String,Object>();
            header.put("alg","HMAC256");
            header.put("typ","JWT");
            b.withHeader(header);


            //토큰 payload 작성, key - value 형식, 객체도 가능
            b.withIssuer(ISSUER);
            b.withClaim("userIdx", idx);
            b.withClaim("grade",grade);
            //만료날짜지정, 1달로
            if(token.equals("accessToken")){
                b.withExpiresAt(accessTokenexpireAt());
            }else if(token.equals("refreshToken")){
                b.withExpiresAt(expireAt());
            }


            //signature : 헤더의 인코딩값과, 정보의 인코딩값을 합친후 주어진 비밀키로 해쉬를 하여 생성합니다.
            return b.sign(Algorithm.HMAC256(SECRET));//token
        } catch (JWTCreationException JwtCreationException) {
            log.info(JwtCreationException.getMessage());
        }
        return null;
    }

    private Date accessTokenexpireAt(){
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        //한달은 24*31
        cal.add(Calendar.MINUTE, 120);//2시간 = 120분


        return  cal.getTime();
    }

    private Date expireAt() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DATE, 7);//7일후
        return  cal.getTime();
    }

    public Token decode(final String token) {
////service prover의 외부서비스 등록 등 인가
        try {
            //토큰 해독 객체 생성 sha256
            final JWTVerifier jwtVerifier = require(Algorithm.HMAC256(SECRET)).withIssuer(ISSUER).build();
            //토큰 검증
            DecodedJWT decodedJWT = jwtVerifier.verify(token);

            System.out.print("\ndecodedjwt - header\n");
            System.out.print(decodedJWT.getHeader());
            System.out.print("\ndecodedjwt - signature\n");
            System.out.print(decodedJWT.getSignature());
            System.out.print("\ndecodedjwt - payload\n");
            System.out.print(decodedJWT.getPayload());



            System.out.print("\ndecodedjwt22\n");
            System.out.print(decodedJWT.getClaim("userIdx").asLong());

            System.out.print("\ndecodedjwt33\n");
            System.out.print(decodedJWT.getClaim("grade").asString());

            //토큰 payload 반환, 정상적인 토큰이라면 토큰 주인(사용자) 고유 ID, 아니라면 -1
            return new Token(decodedJWT.getClaim("userIdx").asLong().intValue(),
                    decodedJWT.getClaim("grade").asString().toString());
        } catch (JWTVerificationException jve) {
            log.error(jve.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return new Token();
    }

    public static class Token {
        //토큰에 담길 정보 필드
        //초기값을 -1로 설정함으로써 로그인 실패시 -1반환
        private int userIdx;
        private String grade;//초기값 : 일반유저값

        public Token() {
            this.userIdx = -1;
            this.grade = "USER";
        }

        public Token(int userIdx,String grade) {
            this.userIdx = userIdx; this.grade = grade;
        }

        public int getUserIdx() {
            return userIdx;
        }
        public String getGrade() {
            return grade;
        }

    }

    //반환될 토큰Response
    @Getter
    @Setter
    public static class TokenResponse {
        //실제 토큰
        private String accessToken;
        private String refreshToken;

        public TokenResponse(final String accessToken,final String refreshToken) {
            this.accessToken = accessToken;
            this.refreshToken = refreshToken;
        }
    }
}