package com.megacoffee.modules.system.auth;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.megacoffee.model.PageVO;
import com.megacoffee.model.SearchVO;

@Mapper
public interface AuthRepository {

    /**
     * 권한 목록 조회
     * @param searching
     * @return
     */
    List<AuthVO> list(SearchVO searching);

    /**
     * 권한 총 개수 조회
     * @param searching
     * @return
     */
    PageVO paging(SearchVO searching);

    /**
     * 권한 단건 조회
     * @param seq
     * @return
     */
    AuthVO item(int seq);

    /**
     * 권한 등록
     * @param auth
     */
    int append(AuthVO auth);

    /**
     * 권한 수정
     * @param auth
     */
    int modify(AuthVO auth);

    /**
     * 권한 삭제
     * @param seqList
     * @param deleteIdx
     */
    int remove(@Param("seqs") List<Long> seqs, @Param("createIdx") Long createIdx);
}
