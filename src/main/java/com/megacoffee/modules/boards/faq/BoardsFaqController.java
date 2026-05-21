package com.megacoffee.modules.boards.faq;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.megacoffee.model.BoardTypeEnum;
import com.megacoffee.modules.boards.BoardsController;

@Controller
@RequestMapping("/boards/faq")
public class BoardsFaqController extends BoardsController {
    @Override
    protected BoardTypeEnum getBoardType() {
        return BoardTypeEnum.FAQ;
    }
}
