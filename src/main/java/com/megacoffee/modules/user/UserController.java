package com.megacoffee.modules.user;

import com.megacoffee.model.SearchVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/admin/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/list")
    public String list(@RequestBody SearchVO searching, Model model) 
    {
        List<UserVO> userList = userService.list(searching);
        int totalCount = userService.count(searching);
        // int totalPages = (int) Math.ceil((double) totalCount / size);
        // model.addAttribute("userList", userList);
        // model.addAttribute("searchType", searchType);
        // model.addAttribute("searchValue", searchValue);
        // model.addAttribute("page", page);
        // model.addAttribute("size", size);
        // model.addAttribute("totalCount", totalCount);
        // model.addAttribute("totalPages", totalPages);
        return "admin/user/list";
    }
}
