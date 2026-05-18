package com.megacoffee.modules.system.permission;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.megacoffee.model.PageVO;
import com.megacoffee.model.SearchVO;

@Mapper
public interface SystemPermissionRepository {

    /**
     * 권한 목록 조회
     * @param searching
     * @return
     */
    List<SystemPermissionVO> list(SearchVO searching);

    /**
     * 권한 전체 목록 조회
     * @return
     */
    List<SystemPermissionVO> listAll();

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
    SystemPermissionVO item(@Param("seq") Long seq);

    /**
     * 권한 등록
     * @param auth
     */
    int append(SystemPermissionVO auth);

    /**
     * 권한 수정
     * @param auth
     */
    int modify(SystemPermissionVO auth);

    /**
     * 권한 1건 삭제
     * @param seqList
     * @param deleteIdx
     */
    int remove(@Param("seq") Long seq, @Param("createIdx") Long createIdx);
    
    /**
     * 권한 여러건 삭제
     * @param seqs
     * @param createIdx
     * @return
     */
    int removes(@Param("seqs") List<Long> seqs, @Param("createIdx") Long createIdx);
}
