package com.rest.user.mapper;

import com.rest.user.dto.request.GetRecruitCalendarRequestDTO;
import com.rest.user.dto.response.GetRecruitCalendarSimpleResponseDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RecruitMapper {
    public List<GetRecruitCalendarSimpleResponseDTO> getRecruitCalendarByDate(
            GetRecruitCalendarRequestDTO getRecruitCalendarRequestDTO);

}
