package com.megacoffee.modules.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
    
    @Autowired
    private MemberRepository repository;

    /**
     * 출퇴근 정보 등록 로직 구현
     * @param request
     * @return
     */
    public int commute(CommuteVO param) {
        int i = repository.appendCommute(param);
        return i;
    }

    public String checkCommute(CommuteVO param){
        repository.checkCommute(param);

        String resultCode = param.getResultCode();

        return resultCode;
    }
}
