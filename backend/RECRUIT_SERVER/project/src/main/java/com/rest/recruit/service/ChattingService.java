package com.rest.recruit.service;

import com.rest.recruit.controller.RecruitController;
import com.rest.recruit.dto.ResultResponse;
import com.rest.recruit.dto.ResultResponseWithoutData;
import com.rest.recruit.dto.SimpleResponse;
import com.rest.recruit.mapper.ChattingMapper;
import com.rest.recruit.model.Chatting;
import com.rest.recruit.model.tmpChatting;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    public ResponseEntity postEnterChatting(int userIdx, int companyIdx) {

        //채팅방 입장
        // 이미있으면 넣지x
        Chatting tmp = chattingMapper.getChatting(userIdx,companyIdx);

        if(tmp != null){

            return SimpleResponse.ok(ResultResponseWithoutData.builder()
                    .message("이미 구독하셨습니다.")
                    .status("400")
                    .success("false")
                    .build());
        }

        int  tmpdetail = chattingMapper.postEnterChatting(userIdx,companyIdx);

        if(tmpdetail > 0){

            return SimpleResponse.ok(ResultResponseWithoutData.builder()
                        .message("채팅방 구독 성공")
                        .status("200")
                        .success("true")
                        .build());
        }else{
            return SimpleResponse.ok(ResultResponseWithoutData.builder()
                        .message("채팅방 구독 실패")
                        .status("400")
                        .success("true")
                        .build());
        }
    }


    public ResponseEntity postEscapeChatting(int userIdx, int companyIdx) {

        Chatting tmp = chattingMapper.getChatting(userIdx,companyIdx);

        if(tmp != null){
            int  tmpdetail = chattingMapper.postEscapeChatting(userIdx,companyIdx);

            if(tmpdetail > 0){

                return SimpleResponse.ok(ResultResponseWithoutData.builder()
                        .message("채팅방 구독 취소 성공")
                        .status("200")
                        .success("true")
                        .build());
            }else{
                return SimpleResponse.ok(ResultResponseWithoutData.builder()
                        .message("채팅방 구독 취소 실패")
                        .status("400")
                        .success("true")
                        .build());
            }
        }else{
            return SimpleResponse.ok(ResultResponseWithoutData.builder()
                    .message("채팅방 구독 조회 결과가 없습니다.")
                    .status("400")
                    .success("true")
                    .build());
        }

    }


}
