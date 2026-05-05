package com.megacoffee.modules.system.menu;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.megacoffee.model.ResultVO;
import com.megacoffee.model.SearchVO;

@Controller
@RequestMapping("/system/menu")
public class SystemMenuController {
    
    @Autowired
    private SystemMenuService service;

    /**
     * 메뉴 목록 조회
     * @param param
     * @return
     */
    @GetMapping({"", "/", "/index"})
    public ModelAndView index(SearchVO param) {
        ModelAndView mav = new ModelAndView("system/menu");
        mav.addObject("searching", param);

        return mav;
    }

    /**
     * 메뉴 목록 조회 (페이징 포함)
     * @param param
     * @return
     */
    @PostMapping("/list")
    public @ResponseBody ResultVO list(@RequestBody SearchVO param) {   
        List<SystemMenuVO> list = service.list(param);

        return new ResultVO(200, "Success", list);
    }

    /**
     * 메뉴 등록
     * @param auth
     * @return
     */
    @PostMapping("/append")
    public @ResponseBody ResultVO append(@RequestBody SystemMenuVO data) {
        int count = service.append(data);

        ResultVO result = new ResultVO();
        result.setCode(count == 1 ? 200 : 500);
        result.setMessage(count == 1 ? "Success" : "Failed");
        result.setData(data);

        return result;
    }

    /**
     * 메뉴 수정
     * @param auth
     * @return
     */
    @PostMapping("/modify")
    public @ResponseBody ResultVO modify(@RequestBody SystemMenuVO data) {
        int count = service.modify(data);

        ResultVO result = new ResultVO();
        result.setCode(count == 1 ? 200 : 500);
        result.setMessage(count == 1 ? "Success" : "Failed");
        result.setData(data);

        return result;
    }

    /**
     * 메뉴들 삭제
      * @param seqs
     * @param seqs
     * @return
     */
    @PostMapping("/delete")
    public @ResponseBody ResultVO delete(@RequestBody List<Long> seqs) {
        int count = 0;
        if (seqs != null && !seqs.isEmpty()) {
            count = service.removes(seqs);
        }

        ResultVO result = new ResultVO();
        result.setCode(count > 0? 200 : 500);
        result.setMessage(count > 0 ? "Success" : "Failed");
        result.setData(count);

        return result;
    }
}