package com.megacoffee.modules.system.permission;

import com.megacoffee.model.BaseVO;

public class SystemPermissionVO extends BaseVO{
    private int seq;
    private String name;
    private int sorting;
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
    public int getSorting() {
        return sorting;
    }
    public void setSorting(int sorting) {
        this.sorting = sorting;
    }
}