package com.megacoffee.modules.system.auth;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.megacoffee.model.PageVO;
import com.megacoffee.model.ResultVO;
import com.megacoffee.model.SearchVO;

@Controller
@RequestMapping("/system/auth")
public class AuthController {
    @Autowired
    private AuthService service;

    /**
     * 권한 목록 조회
     * @param param
     * @return
     */
    @GetMapping({"", "/", "/index"})
    public ModelAndView index(SearchVO param) {
        ModelAndView mav = new ModelAndView("system/auth/index");
        mav.addObject("searching", param);

        return mav;
    }

    /**
     * 권한 목록 조회 (페이징 포함)
     * @param param
     * @return
     */
    @PostMapping("/list")
    public @ResponseBody ResultVO list(@RequestBody SearchVO param) {   
        PageVO paging = service.paging(param);
        List<AuthVO> list = service.list(param);

        return new ResultVO(200, "Success", list, paging);
    }

    /**
     * 권한 등록
     * @param auth
     * @return
     */
    @PostMapping("/append")
    public @ResponseBody ResultVO append(@RequestBody AuthVO auth) {
        int count = service.append(auth);

        ResultVO result = new ResultVO();
        result.setCode(count == 1 ? 200 : 500);
        result.setMessage(count == 1 ? "Success" : "Failed");
        result.setData(auth);

        return result;
    }

    /**
     * 권한 수정
     * @param auth
     * @return
     */
    @PostMapping("/modify")
    public @ResponseBody ResultVO modify(@RequestBody AuthVO auth) {
        int count = service.modify(auth);

        ResultVO result = new ResultVO();
        result.setCode(count == 1 ? 200 : 500);
        result.setMessage(count == 1 ? "Success" : "Failed");
        result.setData(auth);

        return result;
    }

    /**
     * 권한들 삭제
      * @param seqs
     * @param seqs
     * @return
     */
    @PostMapping("/delete")
    public @ResponseBody ResultVO delete(@RequestBody List<Long> seqs) {
        int count = 0;
        if (seqs != null && !seqs.isEmpty()) {
            count = service.remove(seqs);
        }

        ResultVO result = new ResultVO();
        result.setCode(count > 0? 200 : 500);
        result.setMessage(count > 0 ? "Success" : "Failed");
        result.setData(count);

        return result;
    }
}