package com.megacoffee.modules.admin.schedule;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 일정 정보 Service
 */
@Service
public class ScheduleService {
    // Repository
    @Autowired
    private ScheduleRepository repo;

    /**
     * 일정 정보 추가
     * @param vo
     * @return
     */
    public int append(ScheduleVO vo) {
        return repo.append(vo);
    }

    /**
     * 일정 정보 수정
     * @param vo
     * @return
     */
    public int modify(ScheduleVO vo) {
        return repo.modify(vo);
    }

    /**
     * 일정 정보 삭제
     * @param vo
     * @return
     */
    public int remove(ScheduleVO vo) {
        return repo.remove(vo);
    }

    /**
     * 일정 정보 목록 조회
     * @param startDate
     * @param endDate
     * @return
     */
    public List<ScheduleVO> list(String startDate, String endDate) {
        return repo.list(startDate, endDate);
    }

    /**
     * 일정 정보 조회
     * @param seq
     * @return
     */
    public ScheduleVO item(int seq) {
        return repo.item(seq);
    }
}
