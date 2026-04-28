package com.megacoffee.modules.system.menu;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.megacoffee.model.SearchVO;

@Mapper
public interface MenuRepository {
    /**
     * 모든 메뉴 조회
     * @return 메뉴 리스트
     */
    List<MenuVO> list(SearchVO searching);

    /**
     * 사용자 권한에 맞는 메뉴 목록 조회
     * @param userSeq
     * @return
     */
    List<MenuVO> listByUserSeq(int userSeq);

    /**
     * 특정 메뉴 조회
     * @param seq 메뉴 시퀀스
     * @return 메뉴 정보
     */
    MenuVO item(int seq);

    /**
     * 메뉴 추가
     * @param menuVO 추가할 메뉴 정보
     */
    void append(MenuVO menuVO);

    /**
     * 메뉴 수정
     * @param menuVO 수정할 메뉴 정보
     */
    void modify(MenuVO menuVO);

    /**
     * 메뉴 삭제
     * @param seq 삭제할 메뉴 시퀀스
     */
    void remove(int seq, int createSeq);
}
