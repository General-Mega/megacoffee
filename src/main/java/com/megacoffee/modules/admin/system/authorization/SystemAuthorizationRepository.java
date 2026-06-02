package com.megacoffee.modules.admin.system.authorization;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.megacoffee.modules.admin.system.menu.SystemMenuVO;
import com.megacoffee.modules.admin.system.permission.SystemPermissionVO;

@Mapper
public interface SystemAuthorizationRepository {
    
    /**
     * 권한 전체 목록 조회
     * @return
     */
    List<SystemPermissionVO> list();

    /**
     * 권한의 메뉴 목록 조회
     * @param authSeq
     * @return
     */
    List<SystemMenuVO> menus(@Param("authSeq") Long authSeq, @Param("menuSeq") Long menuSeq);

    /**
     * 권한의 메뉴 목록 전체 삭제
     * @param authSeq
     * @return
     */
    int removes(@Param("authSeq") Long authSeq) throws Exception;

    /**
     * 권한의 메뉴 목록 일괄 추가
     * @param authSeq
     * @param menuSeqs
     * @return
     */
    int append(@Param("authSeq") Long authSeq, @Param("menuSeqs") List<Long> menuSeqs) throws Exception;
}
