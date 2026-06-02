package com.megacoffee.modules.admin.boards.general;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.megacoffee.model.BoardTypeEnum;
import com.megacoffee.modules.admin.boards.BoardsController;

@Controller
@RequestMapping("/admin/boards/general")
public class BoardsGeneralController extends BoardsController {
    @Override
    protected BoardTypeEnum getBoardType() {
        return BoardTypeEnum.GENERAL;
    }
}
