package com.megacoffee.modules.system.auth;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.megacoffee.model.SearchVO;

@Service
public class AuthService {
    @Autowired
    private AuthRepository repository;

    public List<AuthVO> list(SearchVO searching) {
        return repository.list(searching);
    }

    public int count(SearchVO searching) {
        return repository.count(searching);
    }

    public AuthVO item(int seq) {
        return repository.item(seq);
    }

    public void append(AuthVO auth, Long userSeq) {
        auth.setCreateIdx(userSeq);
        repository.append(auth);
    }

    public void modify(AuthVO auth, Long userSeq) {
        auth.setUpdateIdx(userSeq);
        repository.modify(auth);
    }

    public void remove(List<Integer> seqList, Long userSeq) {
        if (seqList == null || seqList.isEmpty()) {
            return;
        }
        repository.remove(seqList, userSeq);
    }
}
