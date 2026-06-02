package com.megacoffee.modules.admin.system.user;

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

@Controller
@RequestMapping("/admin/system/user")
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
    public ModelAndView index(SystemUserSearchVO param) {
        ModelAndView mav = new ModelAndView("admin/system/user");
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
    public @ResponseBody ResultVO list(@RequestBody SystemUserSearchVO param) {
        PageVO paging = service.paging(param);
        List<SystemUserVO> list = service.list(param);

        return new ResultVO(200, "Success", list, paging);
    }

    @PostMapping("/append")
    public @ResponseBody ResultVO append(@RequestBody SystemUserVO user) {
        if (user == null || user.getUserId() == null || user.getUserId().trim().isEmpty()) {
            return new ResultVO(500, "아이디를 입력해주세요.");
        }
        if (service.idCheck(user.getUserId())) {
            return new ResultVO(500, "이미 사용 중인 아이디입니다.");
        }

        int count = service.append(user);

        ResultVO result = new ResultVO();
        result.setCode(count == 1 ? 200 : 500);
        result.setMessage(count == 1 ? "Success" : "Failed");
        result.setData(user);

        return result;
    }

    @PostMapping("/idCheck")
    public @ResponseBody ResultVO idCheck(@RequestBody SystemUserVO user) {
        boolean isDuplicate = service.idCheck(user.getUserId());
        return new ResultVO(200, "Success", isDuplicate);
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
