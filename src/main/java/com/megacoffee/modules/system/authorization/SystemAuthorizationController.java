package com.megacoffee.modules.system.authorization;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.megacoffee.model.ResultVO;
import com.megacoffee.modules.system.menu.SystemMenuVO;
import com.megacoffee.modules.system.permission.SystemPermissionVO;

@Controller
@RequestMapping("/system/authorization")
public class SystemAuthorizationController {
    @Autowired
    private SystemAuthorizationService service;

    @GetMapping({"", "/", "/index"})
    public ModelAndView index() {
        List<SystemPermissionVO> list = service.list();
        
        ModelAndView mav = new ModelAndView("system/authorization");
        mav.addObject("list", list);

        return mav;
    }

    @PostMapping("/{authSeq}/menus")
    public @ResponseBody ResultVO menus(@PathVariable Long authSeq) {
        List<SystemMenuVO> list = service.menus(authSeq);

        return new ResultVO(200, "Success", list);
    }

    @PostMapping("/{authSeq}/save")
    public @ResponseBody ResultVO saveMenuForAuth(@PathVariable Long authSeq, @RequestBody List<Long> menuSeqs) {
        int count = 0 ;
        
        ResultVO result = new ResultVO();
        try{
            count = service.save(authSeq, menuSeqs);

            result.setCode(200);
            result.setMessage("Saved");
            result.setData(count);
        } catch (Exception e) {
            e.printStackTrace();
            result.setCode(500);
            result.setMessage("Failed");
        }

        return result;
    }
}
