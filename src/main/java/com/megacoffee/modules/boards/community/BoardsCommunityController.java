package com.megacoffee.modules.boards.community;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.megacoffee.model.BoardTypeEnum;
import com.megacoffee.modules.boards.BoardsController;

@Controller
@RequestMapping("/boards/community")
public class BoardsCommunityController extends BoardsController {
    @Override
    protected BoardTypeEnum getBoardType() {
        return BoardTypeEnum.COMMUNITY;
    }
}
