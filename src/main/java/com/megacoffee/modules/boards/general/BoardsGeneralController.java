package com.megacoffee.modules.boards.general;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.megacoffee.model.BoardTypeEnum;
import com.megacoffee.modules.boards.BoardsController;

@Controller
@RequestMapping("/boards/general")
public class BoardsGeneralController extends BoardsController {
    @Override
    protected BoardTypeEnum getBoardType() {
        return BoardTypeEnum.GENERAL;
    }
}
