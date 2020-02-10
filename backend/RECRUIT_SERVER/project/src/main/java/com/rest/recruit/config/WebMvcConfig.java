package com.rest.recruit.config;

import com.rest.recruit.interceptor.JwtCheckInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@Slf4j
public class WebMvcConfig implements WebMvcConfigurer {


    private String[] INTERCEPTOR_WHITE_LIST = {
            "/recruits/detail/**",
            "/recruits/calendar/**",
            "/ranking/visit",
            "/ranking/like",
            "/ranking/apply",
            "/chats/hot"
    };

    @Autowired
    private JwtCheckInterceptor jwtCheckInterceptor;

    //채용공고 즐겨찾기, 즐겨찾기 취소, 채팅 구독, 채팅 구독 취소, 유저의채팅목록,유저의 자소서작성 회사 채팅목록
//상세채용,캘린더, 조회랭킹,지원랭킹,좋아요랭킹, 인기채팅방 제외

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtCheckInterceptor)
                .addPathPatterns("/*")
                .excludePathPatterns(INTERCEPTOR_WHITE_LIST);

    }



}
