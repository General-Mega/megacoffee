package com.megacoffee.modules.system.auth;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.megacoffee.infra.Security;
import com.megacoffee.model.PageVO;
import com.megacoffee.model.SearchVO;

@Service
public class SystemAuthService {
    @Autowired
    private SystemAuthRepository repo;

    /**
     * 권한 목록 조회
     * @param searching
     * @return
     */
    public List<SystemAuthVO> list(SearchVO searching) {
        return repo.list(searching);
    }

    /**
     * 권한 총 개수 조회
     * @param searching
     * @return
     */
    public PageVO paging(SearchVO searching) {
        return repo.paging(searching);
    }

    /**
     * 권한 단건 조회
     * @param seq
     * @return
     */
    public SystemAuthVO item(Long seq) {
        return repo.item(seq);
    }

    /**
     * 권한 등록
     * @param auth
     * @param userSeq
     */
    public int append(SystemAuthVO auth) {
        Long createIdx = Security.idx();
        auth.setCreateIdx(createIdx);

        return repo.append(auth);
    }

    /**
    * 권한 수정
    * @param auth
    * @param userSeq
    */
    public int modify(SystemAuthVO auth) {
        Long createIdx = Security.idx();
        auth.setCreateIdx(createIdx);

        return repo.modify(auth);
    }

    /**
     * 권한 삭제
     * @param seqList
     * @param userSeq
     */
    public int remove(Long seq) {
        Long createIdx = Security.idx();
        
        return repo.remove(seq, createIdx);
    }

    /**
     * 권한 일괄 삭제
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
}
