package com.rest.recruit.controller;

import com.rest.recruit.dto.ResultResponseWithoutData;
import com.rest.recruit.dto.request.DataWithToken;
import com.rest.recruit.model.Chatting;
import com.rest.recruit.service.ChattingService;
import com.rest.recruit.util.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@CrossOrigin("*")
@Api(tags={"채팅"})
@RestController
@RequestMapping("/chats")
public class ChattingController {

    private final ChattingService chattingService;

    @Autowired
    private JwtUtil jwtUtil;

    public ChattingController(ChattingService chattingService) {
        this.chattingService = chattingService;
    }


    @ApiOperation(value = "유저의 채팅 리스트 조회", httpMethod = "GET",
            notes = "유저의 채팅 리스트 조회" , response= Chatting.class)
    @GetMapping("/my")
    public ResponseEntity getUserChatting(@ApiParam(value = "Authorization", required = true)
                                   @RequestHeader(value="Authorization") String token) {

        String tokenString = token.substring("Bearer ".length());
        return chattingService.GetUserChattingList(jwtUtil.getAuthentication(tokenString));
    }


    @ApiOperation(value = "즐겨찾기/ 작성한 기업 채팅 리스트 조회", httpMethod = "GET",
            notes = "즐겨찾기/ 작성한 기업 채팅 리스트 조회" , response= Chatting.class)
    @GetMapping("/favorite")
    public ResponseEntity getFavoriteChatting(@ApiParam(value = "Authorization", required = true)
                                   @RequestHeader(value="Authorization") String token)  {

        String tokenString = token.substring("Bearer ".length());
        return chattingService.GetFavoriteChattingList(jwtUtil.getAuthentication(tokenString));
    }


    @ApiOperation(value = "인기 채팅 리스트 조회", httpMethod = "GET",
            notes = "인기 채팅  리스트 조회" , response= Chatting.class)
    @GetMapping("/hot")
    public ResponseEntity getHotChatting()  {
        return chattingService.GetHotChattingList();
    }


    @ApiOperation(value = "채팅 구독", httpMethod = "POST", notes = "채팅 구독",response= ResultResponseWithoutData.class)
    @PostMapping("/detail/{companyIdx}/like")
    public ResponseEntity postEnterChatting(@ApiParam(value = "companyIdx, token", required = true)
                                     @RequestHeader(value="Authorization") String token,
                                     @PathVariable(value = "companyIdx") int companyIdx) {

        log.info("채팅구독 api");
        System.out.print(companyIdx);

        String tokenString = token.substring("Bearer ".length());
        return chattingService.postEnterChatting(jwtUtil.getAuthentication(tokenString),companyIdx);
    }

    @ApiOperation(value = "채팅 구독 취소", httpMethod = "DELETE", notes = "채팅 구독 취소",response= ResultResponseWithoutData.class)
    @DeleteMapping("/detail/{companyIdx}/unlike")
    public ResponseEntity postEscapeChatting(@ApiParam(value = "companyIdx, token", required = true)
                                       @RequestHeader(value="Authorization") String token,
                                       @PathVariable(value = "companyIdx") int companyIdx) {

        String tokenString = token.substring("Bearer ".length());
        return chattingService.postEscapeChatting(jwtUtil.getAuthentication(tokenString),companyIdx);
    }

}