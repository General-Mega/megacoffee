package com.megacoffee.modules.system.menu;

import java.util.List;

import com.megacoffee.model.BaseVO;

/**
 * 메뉴 정보 VO 클래스
 * - 메뉴의 계층 구조를 표현하기 위해 parentSeq, depth, children 필드 포함
 * - URL 매칭을 위한 matchUrl 필드 포함
 * - 메뉴 정렬을 위한 sorting 필드 포함
 */
public class MenuVO extends BaseVO{
    private int seq;
    private String name;
    private int parentSeq;
    private String parentName;
    private String url;
    private String matchUrl;
    private int depth;
    private int sorting;

    private List<MenuVO> children;

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getParentSeq() {
        return parentSeq;
    }

    public void setParentSeq(int parentSeq) {
        this.parentSeq = parentSeq;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMatchUrl() {
        return matchUrl;
    }

    public void setMatchUrl(String matchUrl) {
        this.matchUrl = matchUrl;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public int getSorting() {
        return sorting;
    }

    public void setSorting(int sorting) {
        this.sorting = sorting;
    }

    public List<MenuVO> getChildren() {
        return children;
    }

    public void setChildren(List<MenuVO> children) {
        this.children = children;
    }

    
}