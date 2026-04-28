package com.megacoffee.modules.system.auth;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.megacoffee.model.SearchVO;

@Controller
@RequestMapping("/system/auth")
public class AuthController {
    // 권한 관리 페이지
    @GetMapping({"", "/"})
    public ModelAndView index(SearchVO param) {
        ModelAndView mav = new ModelAndView("system/auth/list");
        mav.addObject("searching", param);

        return mav;
    }   
}