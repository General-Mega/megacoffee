package com.megacoffee.modules.system.menu;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.megacoffee.model.SearchVO;

@Mapper
public interface SystemMenuRepository {
    /**
     * 모든 메뉴 조회
     * @return 메뉴 리스트
     */
    List<SystemMenuVO> list(SearchVO searching);

    /**
     * 사용자 권한에 맞는 메뉴 목록 조회
     * @param userSeq
     * @return
     */
    List<SystemMenuVO> listByUserSeq(Long userSeq);

    /**
     * 특정 메뉴 조회
     * @param seq 메뉴 관리번호
     * @return 메뉴 정보
     */
    SystemMenuVO item(Long seq);

    /**
     * 메뉴 추가
     * @param menuVO 추가할 메뉴 정보
     */
    int append(SystemMenuVO menuVO);

    /**
     * 메뉴 수정
     * @param menuVO 수정할 메뉴 정보
     */
    int modify(SystemMenuVO menuVO);

    /**
     * 메뉴 삭제
     * @param seq 삭제할 메뉴 관리번호
     * @param createSeq 삭제자 관리번호
     */
    int remove(Long seq, Long createSeq);

    int removes(List<Long> seqs, Long createSeq);
}
