package com.rest.recruit.mapper;

import com.rest.recruit.model.Chatting;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ChattingMapper {

    public List<Chatting> getUserChattingList(int userIdx);

    public List<Chatting> getFavoriteChattingList(int userIdx);

    public List<Chatting> getHotChattingList();
}
