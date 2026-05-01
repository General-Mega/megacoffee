package com.megacoffee.modules.system.auth;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.megacoffee.infra.Security;
import com.megacoffee.model.PageVO;
import com.megacoffee.model.SearchVO;

@Service
public class AuthService {
    @Autowired
    private AuthRepository repository;

    /**
     * 권한 목록 조회
     * @param searching
     * @return
     */
    public List<AuthVO> list(SearchVO searching) {
        return repository.list(searching);
    }

    /**
     * 권한 총 개수 조회
     * @param searching
     * @return
     */
    public PageVO paging(SearchVO searching) {
        return repository.paging(searching);
    }

    /**
     * 권한 단건 조회
     * @param seq
     * @return
     */
    public AuthVO item(int seq) {
        return repository.item(seq);
    }

    /**
     * 권한 등록
     * @param auth
     * @param userSeq
     */
    public int append(AuthVO auth) {
        Long createIdx = Security.idx();
        auth.setCreateIdx(createIdx);

        return repository.append(auth);
    }

    /**
    * 권한 수정
    * @param auth
    * @param userSeq
    */
    public int modify(AuthVO auth) {
        Long createIdx = Security.idx();
        auth.setCreateIdx(createIdx);

        return repository.modify(auth);
    }

    /**
     * 권한 삭제
     * @param seqList
     * @param userSeq
     */
    public int remove(List<Long> seqs) {
        if (seqs == null || seqs.isEmpty()) {
            return 0;
        }
        Long createIdx = Security.idx();
        
        return repository.remove(seqs, createIdx);
    }
}
