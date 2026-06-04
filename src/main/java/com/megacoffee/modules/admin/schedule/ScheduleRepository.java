package com.megacoffee.modules.admin.schedule;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 일정 정보 Repository
 */
@Mapper
public interface ScheduleRepository {
    // 일정 정보 추가
    public int append(ScheduleVO vo);   

    // 일정 정보 수정
    public int modify(ScheduleVO vo);

    // 일정 정보 삭제
    public int remove(ScheduleVO vo);

    // 일정 정보 목록 조회
    public List<ScheduleVO> list(@Param("startDate") String startDate, @Param("endDate") String endDate);

    // 일정 정보 조회
    public ScheduleVO item(@Param("seq") int seq);
}