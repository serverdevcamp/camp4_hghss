package com.rest.recruit.mapper;

import com.rest.recruit.dto.request.DataWithToken;
import com.rest.recruit.dto.request.GetRecruitCalendarRequestDTO;
import com.rest.recruit.dto.response.GetRecruitCalendarSimpleResponseDTO;
import com.rest.recruit.model.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RecruitMapper {
    public List<GetRecruitCalendarSimpleResponseDTO> getRecruitCalendarByDate(
            GetRecruitCalendarRequestDTO getRecruitCalendarRequestDTO);

    public RecruitDetail GetDetailRecruitPage(DataWithToken dataWithToken);

    public List<Position> getPosition(int recruitIdx);

    public List<SimpleRecruit> getSimpleRecruit();

    public int updateViewCount(int recruitIdx, int viewCount);

    public SimpleRecruit getSimpleRecruitById(int recruitIdx);

    public int updateViewCountWithDB(int recruitIdx);

    public RecruitLike GetFavorite(int userIdx, int recruitIdx);

    public int PostUnlikeRecruit(DataWithToken dataWithToken);

    public int PostLikeRecruit(DataWithToken dataWithToken);

    public int PostLikeRecruitCount(int recruitIdx);

    public int PostUnlikeRecruitCount(int recruitIdx);

    List<Calendars> getRecruitCalendar(GetRecruitCalendarRequestDTO getRecruitCalendarRequestDTO);

    List<Integer> GetUserLikeList(GetRecruitCalendarRequestDTO getRecruitCalendarRequestDTO);

    public Recruit GetRecruit(int recruitIdx);

    public int GetViewCount(int recruitId);

    public int GetFavoriteCount(int recruitIdx);
}
