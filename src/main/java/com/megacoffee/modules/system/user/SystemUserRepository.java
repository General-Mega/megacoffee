package com.megacoffee.modules.system.user;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.megacoffee.model.PageVO;
import com.megacoffee.model.SearchVO;

@Mapper
public interface SystemUserRepository {

    /**
     * 사용자 정보 목록 조회
     * @param searching
     * @return
     */
    List<SystemUserVO> list(SearchVO searching);

    /**
     * 사용자 총 개수 조회 (페이징용)
     * @param searching
     * @return
     */
    PageVO paging(SearchVO searching);

    /**
     * 사용자 정보 단건 조회
     * @param seq
     * @return
     */
    SystemUserVO item(@Param("seq") Long seq);

    /**
     * 사용자 등록
     * @param user
     * @return
     */
    int append(SystemUserVO user);

    /**
     * 사용자 정보 수정
     * @param user
     * @return
     */
    int modify(SystemUserVO user);

    /**
     * 사용자 정보 1건 삭제
     * @param seq
     * @param createIdx
     * @return
     */
    int remove(@Param("seq") Long seq, @Param("createIdx") Long createIdx);

    /**
     * 사용자 정보 여러건 삭제
     * @param seqs
     * @param createIdx
     * @return
     */
    int removes(@Param("seqs") List<Long> seqs, @Param("createIdx") Long createIdx);

    //  비밀번호 초기화 요청
    int setPasswordReset(@Param("seq") Long seq);

    // 마지막 로그인 시간 업데이트
    int updateLastLogin(@Param("seq") Long seq);

    // 사용자 ID로 사용자 정보 조회
    SystemUserVO itemByUserID(@Param("userId") String userId);




    // 사용자 ID 중복 확인
    boolean idCheck(@Param("userId") String userId);
}