package com.megacoffee.modules.user;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.megacoffee.model.SearchVO;

@Mapper
public interface UserRepo {

    // 사용자 등록
    int append(UserVO user);

    // 사용자 정보 수정
    int modify(UserVO user);

    // 사용자 정보 삭제
    int remove(@Param("seq") Long seq, @Param("createIdx") Long createIdx);

    //  비밀번호 초기화 요청
    int setPasswordReset(@Param("seq") Long seq);

    // 마지막 로그인 시간 업데이트
    int updateLastLogin(@Param("seq") Long seq);

    // 사용자 ID로 사용자 정보 조회
    UserVO itemByUserID(@Param("userId") String userId);

    // 사용자 관리번호로 사용자 정보 조회
    UserVO item(@Param("seq") Long seq);


    // 사용자 정보 목록 조회
    List<UserVO> list(SearchVO param);

    // 사용자 총 개수 조회 (페이징용)
    int count(SearchVO param);

    // 사용자 ID 중복 확인
    boolean idCheck(@Param("userId") String userId);
}