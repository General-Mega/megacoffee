package com.megacoffee.modules.system.menu;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.megacoffee.infra.Security;
import com.megacoffee.model.SearchVO;

/**
 * 메뉴 서비스
 */
@Service
public class SystemMenuService {

    @Autowired
    private SystemMenuRepository repo;

    /**
     * 모든 메뉴 조회
     * @return 메뉴 리스트
     */
    public List<SystemMenuVO> list(SearchVO searching) {
        List<SystemMenuVO> menus = repo.list(searching);

        if(menus == null || menus.isEmpty()) {
            return List.of();
        }

        List<SystemMenuVO> list = menus.stream().filter(m -> m.getParentSeq() == 0).toList();
        setupChildren(list, menus);

        return list;
    }

    /**
     * 사용자 권한에 맞는 메뉴 목록 조회
     * @param userSeq
     * @return
     */
    public List<SystemMenuVO> listByUserSeq(Long userSeq) {
        List<SystemMenuVO> menus = repo.listByUserSeq(userSeq);

        if(menus == null || menus.isEmpty()) {
            return List.of();
        }

        List<SystemMenuVO> list = menus.stream().filter(m -> m.getParentSeq() == null).toList();
        setupChildren(list, menus);

        return list;
    }

    /**
     * 하위 메뉴 설정
     * @param targets
     * @param list
     */
    private void setupChildren(List<SystemMenuVO> targets, List<SystemMenuVO> list){
        targets.forEach(m -> {
            List<SystemMenuVO> children = list.stream().filter(c -> c.getParentSeq() == m.getSeq()).toList();
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
    public SystemMenuVO item(Long seq) {
        return repo.item(seq);
    }

    /**
     * 메뉴 추가
     * @param data
     */
    public int append(SystemMenuVO data) {
        Long createIdx = Security.idx();
        data.setCreateIdx(createIdx);

        return repo.append(data);
    }

    /**
     * 메뉴 수정
     * @param data
     */
    public int modify(SystemMenuVO data) {
        Long createIdx = Security.idx();
        data.setCreateIdx(createIdx);

        return repo.modify(data);
    }

    /**
     * 메뉴 삭제
     * @param seq
     * @param createSeq
     */
    public int remove(Long seq) {
        Long createIdx = Security.idx();

        return repo.remove(seq, createIdx);
    }

    public int removes(List<Long> seqs){
        if (seqs == null || seqs.isEmpty()) {
            return 0;
        }
        Long createIdx = Security.idx();

        return repo.removes(seqs, createIdx);
    }
}
