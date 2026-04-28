package com.megacoffee.modules.system.auth;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.megacoffee.model.SearchVO;

@Mapper
public interface AuthRepository {
    List<AuthVO> list(SearchVO searching);
    int count(SearchVO searching);
    AuthVO item(int seq);
    void append(AuthVO auth);
    void modify(AuthVO auth);
    void remove(@Param("seqList") List<Integer> seqList, @Param("deleteIdx") Long deleteIdx);
}
