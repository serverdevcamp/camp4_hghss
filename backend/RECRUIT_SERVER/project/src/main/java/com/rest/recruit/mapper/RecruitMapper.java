package com.rest.recruit.mapper;

import com.rest.recruit.dto.request.DataWithToken;
import com.rest.recruit.dto.request.GetRecruitCalendarRequestDTO;
import com.rest.recruit.dto.response.GetRecruitCalendarSimpleResponseDTO;
import com.rest.recruit.model.Position;
import com.rest.recruit.model.RecruitDetail;
import com.rest.recruit.model.RecruitLike;
import com.rest.recruit.model.SimpleRecruit;
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

}
