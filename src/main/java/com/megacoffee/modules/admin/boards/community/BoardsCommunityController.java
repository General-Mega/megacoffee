package com.megacoffee.modules.admin.boards.community;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.megacoffee.model.BoardTypeEnum;
import com.megacoffee.modules.admin.boards.BoardsController;

@Controller
@RequestMapping("/admin/boards/community")
public class BoardsCommunityController extends BoardsController {
    @Override
    protected BoardTypeEnum getBoardType() {
        return BoardTypeEnum.COMMUNITY;
    }
}
