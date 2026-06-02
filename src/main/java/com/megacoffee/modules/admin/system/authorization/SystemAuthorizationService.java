package com.megacoffee.modules.admin.system.authorization;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.megacoffee.modules.admin.system.menu.SystemMenuVO;
import com.megacoffee.modules.admin.system.permission.SystemPermissionVO;

@Service
public class SystemAuthorizationService {
    @Autowired
    private SystemAuthorizationRepository repo;

    /**
     * 권한 전체 목록 조회
     * @return
     */
    public List<SystemPermissionVO> list() {
        return repo.list();
    }
    
    /**
     * 권한의 메뉴 목록 조회
     * @param authSeq
     * @return
     */
    public List<SystemMenuVO> menus(Long authSeq) {
        List<SystemMenuVO> list = repo.menus(authSeq, null);
        if(list == null) {
            return List.of();
        }

        for(SystemMenuVO menu : list) {
            setupChildrenMenus(authSeq, menu);
        }

        return list;
    }

    /**
     * 권한의 메뉴 목록 전체 삭제
     * @param authSeq
     * @return
     */
    public int removes(Long authSeq) throws Exception {
        return repo.removes(authSeq);
    }

    /**
     * 권한의 메뉴 목록 일괄 추가
     * @param authSeq
     * @param menuSeqs
     * @return
     */
    public int append(Long authSeq, List<Long> menuSeqs) throws Exception {
        return repo.append(authSeq, menuSeqs);
    }

    /**
     * 권한의 메뉴 목록 전체 삭제 후 일괄 추가
     * @param authSeq
     * @param menus
     * @return
     */
    public int save(Long authSeq, List<Long> menus) throws Exception {
        int count = repo.removes(authSeq);
        count = repo.append(authSeq, menus);

        return count;
    }

    private void setupChildrenMenus(Long authSeq, SystemMenuVO menu){
        Long menuSeq = menu.getSeq();
        List<SystemMenuVO> children = repo.menus(authSeq, menuSeq);
        menu.setChildren(children);

        if(children != null) {
            for(SystemMenuVO child : children) {
                setupChildrenMenus(authSeq, child);
            }
        }
    }
}