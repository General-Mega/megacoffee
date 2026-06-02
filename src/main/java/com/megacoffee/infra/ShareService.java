package com.megacoffee.infra;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.megacoffee.model.SearchVO;
import com.megacoffee.modules.admin.system.permission.SystemPermissionService;
import com.megacoffee.modules.admin.system.permission.SystemPermissionVO;

@Component
public class ShareService {

    @Autowired
    private SystemPermissionService authService;

    /**
     * 공통으로 사용되는 권한 목록 조회
     * @return
     */
    public List<SystemPermissionVO> listAuth(){
        SearchVO param = new SearchVO();
        param.setPage(1);
        param.setDataCount(100);

        List<SystemPermissionVO> list = authService.list(param);
        return list;
    }
}