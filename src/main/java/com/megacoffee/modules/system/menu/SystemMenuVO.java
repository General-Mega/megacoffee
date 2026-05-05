package com.megacoffee.modules.system.menu;

import java.util.List;

import com.megacoffee.model.BaseVO;

public class SystemMenuVO extends BaseVO{
    private Long seq;
    private String name;
    private Long parentSeq;
    private String parentName;
    private String url;
    private String matchUrl;
    private int depth;
    private int sorting;

    private List<SystemMenuVO> children;

    public Long getSeq() {
        return seq;
    }

    public void setSeq(Long seq) {
        this.seq = seq;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getParentSeq() {
        return parentSeq;
    }

    public void setParentSeq(Long parentSeq) {
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

    public List<SystemMenuVO> getChildren() {
        return children;
    }

    public void setChildren(List<SystemMenuVO> children) {
        this.children = children;
    }

    
}
