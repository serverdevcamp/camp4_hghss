package com.rest.recruit.mapper;

import com.rest.recruit.dto.request.GetRecruitCalendarRequestDTO;
import com.rest.recruit.dto.response.GetRecruitCalendarSimpleResponseDTO;
import com.rest.recruit.dto.response.GetRecruitDetailResponseDTO;
import com.rest.recruit.model.Position;
import com.rest.recruit.model.RecruitDetail;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RecruitMapper {
    public List<GetRecruitCalendarSimpleResponseDTO> getRecruitCalendarByDate(
            GetRecruitCalendarRequestDTO getRecruitCalendarRequestDTO);

    public RecruitDetail GetDetailRecruitPage(int recruitIdx);

    List<Position> getPosition(int recruitIdx);
}
