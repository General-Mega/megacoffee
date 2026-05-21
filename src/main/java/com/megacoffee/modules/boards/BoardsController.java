package com.megacoffee.modules.boards;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.megacoffee.model.PageVO;
import com.megacoffee.model.ResultVO;
import com.megacoffee.model.BoardTypeEnum;

public abstract class BoardsController {

    @Autowired
    protected BoardsService service;

    protected abstract BoardTypeEnum getBoardType();

    /**
     * 게시판 메인 화면
     * @param param
     * @return
     */
    @GetMapping({"", "/", "/index"})
    public ModelAndView index(BoardsSearchVO param) {
        param.setType(getBoardType().getCode());
        String boardTypeName = getBoardType().name().toLowerCase();
        ModelAndView mav = new ModelAndView("boards/" + boardTypeName + "/index");
        mav.addObject("searching", param);
        mav.addObject("boardType", getBoardType());
        return mav;
    }

    /**
     * 게시물 상세 화면
     * @param seq
     * @return
     */
    @GetMapping("/detail")
    public ModelAndView detail(@RequestParam("seq") Long seq) {
        BoardsVO item = new BoardsVO();
        item.setSeq(seq);
        item.setType(getBoardType().getCode());

        String boardTypeName = getBoardType().name().toLowerCase();
        ModelAndView mav = new ModelAndView("boards/" + boardTypeName + "/detail");
        mav.addObject("item", service.item(item));
        mav.addObject("boardType", getBoardType());
        return mav;
    }

    /**
     * 게시물 등록/수정 화면
     * @return
     */
    @GetMapping("/form")
    public ModelAndView form(@RequestParam(name="seq", required = false, defaultValue = "0") Long seq) {
        String boardTypeName = getBoardType().name().toLowerCase();

        BoardsVO item = new BoardsVO();
        item.setSeq(seq);
        item.setType(getBoardType().getCode());

        BoardsVO data = service.item(item);

        ModelAndView mav = new ModelAndView("boards/" + boardTypeName + "/form");
        mav.addObject("boardType", getBoardType());
        mav.addObject("item", data);
        mav.addObject("mode", data == null ? "create" : "modify");

        return mav;
    }

    /**
     * 게시물 목록 조회
     * @param param
     * @return
     */
    @PostMapping("/list")
    public @ResponseBody ResultVO list(@RequestBody BoardsSearchVO param) {
        param.setType(getBoardType().getCode());
        PageVO paging = service.paging(param);
        List<BoardsVO> list = service.list(param);

        return new ResultVO(200, "Success", list, paging);
    }

    /**
     * 게시물 등록
     * @param board
     * @return
     */
    @PostMapping("/append")
    public @ResponseBody ResultVO append(@RequestBody BoardsVO board) {
        if (board == null || board.getTitle() == null || board.getTitle().trim().isEmpty()) {
            return new ResultVO(500, "제목을 입력해주세요.");
        }
        board.setType(getBoardType().getCode());
        int count = service.append(board);

        ResultVO result = new ResultVO();
        result.setCode(count == 1 ? 200 : 500);
        result.setMessage(count == 1 ? "Success" : "Failed");
        result.setData(board);
        return result;
    }

    /**
     * 게시물 수정
     * @param board
     * @return
     */
    @PostMapping("/modify")
    public @ResponseBody ResultVO modify(@RequestBody BoardsVO board) {
        if (board == null || board.getSeq() == null) {
            return new ResultVO(500, "수정할 게시물을 찾을 수 없습니다.");
        }
        board.setType(getBoardType().getCode());
        int count = service.modify(board);

        ResultVO result = new ResultVO();
        result.setCode(count == 1 ? 200 : 500);
        result.setMessage(count == 1 ? "Success" : "Failed");
        result.setData(board);
        return result;
    }

    /**
     * 게시물 삭제
     * @param seqs
     * @return
     */
    @PostMapping("/delete")
    public @ResponseBody ResultVO delete(@RequestBody List<Long> seqs) {
        int count = service.removes(seqs, getBoardType().getCode());

        ResultVO result = new ResultVO();
        result.setCode(count > 0 ? 200 : 500);
        result.setMessage(count > 0 ? "Success" : "Failed");
        result.setData(count);
        return result;
    }
}
