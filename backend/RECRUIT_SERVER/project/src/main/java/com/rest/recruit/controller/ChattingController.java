package com.rest.recruit.controller;

import com.rest.recruit.model.Chatting;
import com.rest.recruit.service.ChattingService;
import com.rest.recruit.util.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.json.JSONException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@Api(tags={"채팅"})
@RestController
@RequestMapping("/chats")
public class ChattingController {

    private final ChattingService chattingService;

    public ChattingController(ChattingService chattingService) {
        this.chattingService = chattingService;
    }


    @ApiOperation(value = "유저의 채팅 리스트 조회", httpMethod = "GET",
            notes = "유저의 채팅 리스트 조회" , response= Chatting.class)
    @GetMapping
    public ResponseEntity getUserChatting(@ApiParam(value = "Authorization", required = true)
                                   @RequestHeader(value="Authorization") String token) {

        String tokenString = token.substring("Bearer ".length());
        JwtUtil jwtUtil = new JwtUtil();
        return chattingService.GetUserChattingList(jwtUtil.getAuthentication(tokenString));
    }


    @ApiOperation(value = "즐겨찾기/ 작성한 기업 채팅 리스트 조회", httpMethod = "GET",
            notes = "즐겨찾기/ 작성한 기업 채팅 리스트 조회" , response= Chatting.class)
    @GetMapping("/favorite")
    public ResponseEntity getFavoriteChatting(@ApiParam(value = "Authorization", required = true)
                                   @RequestHeader(value="Authorization") String token)  {

        String tokenString = token.substring("Bearer ".length());
        JwtUtil jwtUtil = new JwtUtil();
        return chattingService.GetFavoriteChattingList(jwtUtil.getAuthentication(tokenString));
    }


    @ApiOperation(value = "인기 채팅 리스트 조회", httpMethod = "GET",
            notes = "인기 채팅  리스트 조회" , response= Chatting.class)
    @GetMapping("/hot")
    public ResponseEntity getHotChatting()  {
        return chattingService.GetHotChattingList();
    }
}