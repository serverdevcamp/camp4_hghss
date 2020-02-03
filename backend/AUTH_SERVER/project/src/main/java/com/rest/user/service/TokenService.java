package com.rest.user.service;

import com.auth0.jwt.JWT;
import com.rest.user.dto.ResultResponse;
import com.rest.user.dto.SimpleResponse;
import com.rest.user.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class TokenService {

    @Autowired
    private jwtService jwtService;

    @Autowired
    RedisTemplate<String,Object> redisTemplate;

    public ResponseEntity refresh(String refreshToken) {


        com.rest.user.service.jwtService.Token checkToken = jwtService.decode(refreshToken);


        //만료된 refreshtoken인지
        if(JWT.decode(refreshToken).getExpiresAt().compareTo(new Date()) >= 1){


            final jwtService.TokenResponse newToken
                    = new jwtService.TokenResponse(
                    jwtService.create(checkToken.getUserIdx(),checkToken.getGrade(),"accessToken"),
                    jwtService.create(checkToken.getUserIdx(),checkToken.getGrade(),"refreshToken")
            );

            //이전 캐시 삭제해야함
            redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer(String.class));
            ValueOperations<String,Object> vop = redisTemplate.opsForValue();
            String getAccessTokenByRedis = (String) vop.get(Integer.toString(checkToken.getUserIdx()));

            if(getAccessTokenByRedis != null){
                //캐시남아있다면 삭제
                redisTemplate.delete(Integer.toString(checkToken.getUserIdx()));
            }

            //뉴 토큰 캐시에 저장
            vop.set(Integer.toString(checkToken.getUserIdx()),newToken.getAccessToken(),120, TimeUnit.MINUTES);

            return SimpleResponse.ok(newToken);

        }else{
            //만료된 refresh token인지 확인한다. (만료되었다면 오류를 반환하여 사용자에게 로그인을 요구한다.) 체크!!
            return SimpleResponse.badRequest();
        }



    }
}
