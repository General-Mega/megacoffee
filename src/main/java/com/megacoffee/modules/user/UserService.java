package com.megacoffee.modules.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.megacoffee.model.SearchVO;

@Service
public class UserService {

    @Autowired
    private UserRepo repo;

    /**
     * 사용자 등록
     * 1. 사용자 ID 중복 확인
     * 2. 사용자 정보 저장
     * @param user
     * @return
     */
    public boolean append(UserVO user) {

        if (repo.idCheck(user.getUserId())) {
            return false;
        }

        repo.append(user);
        
        return true;
    }

    /**
     * 사용자 정보 수정
     * @param user
     */
    public void modify(UserVO user) {
        repo.modify(user);
    }

    /**
     * 사용자 정보 삭제
     * @param seq
     * @param createIdx
     */
    public void remove(Long seq, Long createIdx) {
        repo.remove(seq, createIdx);
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
    public UserVO itemByUserID(String userId) {
        return repo.itemByUserID(userId);
    }

    /**
     * 사용자 정보 확인
     * @param seq
     * @return
     */
    public UserVO item(Long seq) {
        return repo.item(seq);
    }

    /**
     * 사용자 정보 목록 조회
     * @param param
     * @return
     */
    public List<UserVO> list(SearchVO param) {
        return repo.list(param);
    }

    /**
     * 사용자 총 개수 조회 (페이징용)
     */
    public int count(SearchVO param) {
        return repo.count(param);
    }
}