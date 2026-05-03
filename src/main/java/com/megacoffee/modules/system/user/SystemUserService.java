package com.megacoffee.modules.system.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.megacoffee.infra.Security;
import com.megacoffee.model.PageVO;
import com.megacoffee.model.SearchVO;

@Service
public class SystemUserService {

    @Autowired
    private SystemUserRepository repo;

    /**
     * 사용자 정보 목록 조회
     * @param param
     * @return
     */
    public List<SystemUserVO> list(SearchVO param) {
        return repo.list(param);
    }

    /**
     * 사용자 총 개수 조회 (페이징용)
     * @param param
     * @return
     */
    public PageVO paging(SearchVO param) {
        return repo.paging(param);
    }

    /**
     * 사용자 정보 확인
     * @param seq
     * @return
     */
    public SystemUserVO item(Long seq) {
        return repo.item(seq);
    }

    /**
     * 사용자 등록
     * 1. 사용자 ID 중복 확인
     * 2. 사용자 정보 저장
     * @param user
     * @return
     */
    public int append(SystemUserVO user) {
        Long createIdx = Security.idx();
        user.setCreateIdx(createIdx);
        
        return repo.append(user);
    }

    /**
     * 사용자 정보 수정
     * @param user
     * @return
     */
    public int modify(SystemUserVO user) {
        Long createIdx = Security.idx();
        user.setCreateIdx(createIdx);
        
        return repo.modify(user);
    }

    /**
     * 사용자 정보 삭제
     * @param seq
     * @param createIdx
     */
    public int remove(Long seq) {
        Long createIdx = Security.idx();
        return repo.remove(seq, createIdx);
    }

    /**
     * 사용자 정보 여러 건 삭제
     * @param seqs
     * @return
     */
    public int removes(List<Long> seqs) {
        if (seqs == null || seqs.isEmpty()) {
            return 0;
        }
        Long createIdx = Security.idx();
        
        return repo.removes(seqs, createIdx);
    }

    /**
     * 비밀번호 초기화 요청
     * @param seq
     * @return
     */
    public boolean setPasswordReset(Long seq) {
        return repo.setPasswordReset(seq) == 1;
    }

    /**
     * 마지막 로그인 시간 업데이트
     * @param seq
     * @return
     */
    public boolean updateLastLogin(Long seq) {
        return repo.updateLastLogin(seq) == 1;
    }

    /**
     * 사용자 ID로 사용자 정보 조회
     * @param userId
     * @return
     */
    public SystemUserVO itemByUserID(String userId) {
        return repo.itemByUserID(userId);
    }
}