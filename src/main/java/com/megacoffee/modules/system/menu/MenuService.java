package com.megacoffee.modules.system.menu;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.megacoffee.model.SearchVO;

/**
 * 메뉴 서비스
 */
@Service
public class MenuService {

    @Autowired
    private MenuRepository repo;

    /**
     * 모든 메뉴 조회
     * @return 메뉴 리스트
     */
    public List<MenuVO> list(SearchVO searching) {
        List<MenuVO> menus = repo.list(searching);

        if(menus == null || menus.isEmpty()) {
            return List.of();
        }

        List<MenuVO> list = menus.stream().filter(m -> m.getParentSeq() == 0).toList();
        setupChildren(list, menus);

        return list;
    }

    /**
     * 사용자 권한에 맞는 메뉴 목록 조회
     * @param userSeq
     * @return
     */
    public List<MenuVO> listByUserSeq(int userSeq) {
        List<MenuVO> menus = repo.listByUserSeq(userSeq);

        if(menus == null || menus.isEmpty()) {
            return List.of();
        }

        List<MenuVO> list = menus.stream().filter(m -> m.getParentSeq() == 0).toList();
        setupChildren(list, menus);

        return list;
    }

    /**
     * 하위 메뉴 설정
     * @param targets
     * @param list
     */
    private void setupChildren(List<MenuVO> targets, List<MenuVO> list){
        targets.forEach(m -> {
            List<MenuVO> children = list.stream().filter(c -> c.getParentSeq() == m.getSeq()).toList();
            m.setChildren(children);
            if(children != null && !children.isEmpty()) {
                setupChildren(children, list);
            }
        });
    }

    /**
     * 메뉴 정보 조회
     * @param seq
     * @return
     */
    public MenuVO item(int seq) {
        return repo.item(seq);
    }

    /**
     * 메뉴 추가
     * @param menuVO
     */
    public void append(MenuVO menuVO) {
        repo.append(menuVO);
    }

    /**
     * 메뉴 수정
     * @param menuVO
     */
    public void modify(MenuVO menuVO) {
        repo.modify(menuVO);
    }

    /**
     * 메뉴 삭제
     * @param seq
     * @param createSeq
     */
    public void remove(int seq, int createSeq) {
        repo.remove(seq, createSeq);
    }
}
