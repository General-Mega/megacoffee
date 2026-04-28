package com.megacoffee.modules.member;

import org.apache.ibatis.annotations.Mapper;

/**
 * 
 */
@Mapper
public interface MemberRepository {

    // 출퇴근 정보 등록
    int appendCommute(CommuteVO param);

    // 출퇴근 확인
    void checkCommute(CommuteVO param);
}