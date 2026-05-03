package com.megacoffee.modules.system.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.megacoffee.infra.ShareService;
import com.megacoffee.model.PageVO;
import com.megacoffee.model.ResultVO;
import com.megacoffee.model.SearchVO;

@Controller
@RequestMapping("/system/user")
public class SystemUserController {
    @Autowired
    private SystemUserService service;

    @Autowired
    private ShareService share;

    /**
     * 사용자 목록 조회
     * @param param
     * @return
     */
    @GetMapping({"", "/", "/index"})
    public ModelAndView index(SearchVO param) {
        ModelAndView mav = new ModelAndView("system/user/index");
        mav.addObject("searching", param);
        mav.addObject("listAuth", share.listAuth());

        return mav;
    }

    /**
     * 사용자 목록 조회 (페이징 포함)
     * @param param
     * @return
     */
    @PostMapping("/list")
    public @ResponseBody ResultVO list(@RequestBody SearchVO param) {
        PageVO paging = service.paging(param);
        List<SystemUserVO> list = service.list(param);

        return new ResultVO(200, "Success", list, paging);
    }

    @PostMapping("/append")
    public @ResponseBody ResultVO append(@RequestBody SystemUserVO user) {
        int count = service.append(user);

        ResultVO result = new ResultVO();
        result.setCode(count == 1 ? 200 : 500);
        result.setMessage(count == 1 ? "Success" : "Failed");
        result.setData(user);

        return result;
    }

    @PostMapping("/modify")
    public @ResponseBody ResultVO modify(@RequestBody SystemUserVO user) {
        int count = service.modify(user);

        ResultVO result = new ResultVO();
        result.setCode(count == 1 ? 200 : 500);
        result.setMessage(count == 1 ? "Success" : "Failed");
        result.setData(user);

        return result;
    }

    @PostMapping("/delete")
    public @ResponseBody ResultVO delete(@RequestBody List<Long> seqs) {
        int count = service.removes(seqs);

        ResultVO result = new ResultVO();
        result.setCode(count > 0 ? 200 : 500);
        result.setMessage(count > 0 ? "Success" : "Failed");
        result.setData(count);

        return result;
    }
}
