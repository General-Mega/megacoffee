package com.megacoffee.modules.admin.boards;

import com.megacoffee.model.SearchVO;

public class BoardsSearchVO extends SearchVO {
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
