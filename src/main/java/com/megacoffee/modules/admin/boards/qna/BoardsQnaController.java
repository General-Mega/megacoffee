package com.megacoffee.modules.admin.boards.qna;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.megacoffee.model.BoardTypeEnum;
import com.megacoffee.modules.admin.boards.BoardsController;

@Controller
@RequestMapping("/admin/boards/qna")
public class BoardsQnaController extends BoardsController {
    @Override
    protected BoardTypeEnum getBoardType() {
        return BoardTypeEnum.QNA;
    }
}
