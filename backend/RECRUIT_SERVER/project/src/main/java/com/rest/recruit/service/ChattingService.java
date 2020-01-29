package com.rest.recruit.service;

import com.rest.recruit.dto.ResultResponse;
import com.rest.recruit.dto.SimpleResponse;
import com.rest.recruit.mapper.ChattingMapper;
import com.rest.recruit.model.Chatting;
import com.rest.recruit.model.tmpChatting;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Slf4j
@Service
public class ChattingService {

    @Autowired
    ChattingMapper chattingMapper;

    public ResponseEntity GetUserChattingList(int userIdx) {

        List<Chatting> chattingResponseDTOList = chattingMapper.getUserChattingList(userIdx);
        
        HashMap<Integer,tmpChatting> map = new HashMap<Integer,tmpChatting>();

        for (Chatting tmpChats : chattingResponseDTOList) {
            map.put(tmpChats.getCompanyIdx(),new tmpChatting(tmpChats.getCompany(),tmpChats.getLogoUrl()));
        }
        return SimpleResponse.ok(ResultResponse.builder()
                .message("유저의 채팅 리스트 조회 성공")
                .status("200")
                .success("true")
                .data(map).build());
    }

    public ResponseEntity GetFavoriteChattingList(int userIdx) {
////    즐겨찾기Or 작성한기업(마감전) 채팅 리스트 - 자소서를 작성한 기업과 즐겨찾기한 기업의 채팅방이 추가됩니다. (recruit_like or resume쓴 기업 idx)
        List<Chatting> chattingResponseDTOList = chattingMapper.getFavoriteChattingList(userIdx);

        HashMap<Integer,tmpChatting> map = new HashMap<Integer,tmpChatting>();

        for (Chatting tmpChats : chattingResponseDTOList) {
            map.put(tmpChats.getCompanyIdx(),new tmpChatting(tmpChats.getCompany(),tmpChats.getLogoUrl()));
        }
        return SimpleResponse.ok(ResultResponse.builder()
                .message("즐겨찾기 / 작성한기업(마감전) 채팅 리스트 조회 성공")
                .status("200")
                .success("true")
                .data(map).build());
    }

    public ResponseEntity GetHotChattingList() {
        ////인기  채팅 리스트 기준????-  채팅 참여자수
        List<Chatting> chattingResponseDTOList = chattingMapper.getHotChattingList();

        HashMap<Integer,tmpChatting> map = new HashMap<Integer,tmpChatting>();

        for (Chatting tmpChats : chattingResponseDTOList) {
            map.put(tmpChats.getCompanyIdx(),new tmpChatting(tmpChats.getCompany(),tmpChats.getLogoUrl()));
        }
        return SimpleResponse.ok(ResultResponse.builder()
                .message("인기 채팅 리스트 조회 성공")
                .status("200")
                .success("true")
                .data(map).build());
    }
}
