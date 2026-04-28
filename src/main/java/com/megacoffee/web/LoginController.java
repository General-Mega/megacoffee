package com.megacoffee.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.megacoffee.modules.user.UserVO;
import com.megacoffee.modules.user.UserService;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 사용자 등록 페이지
     */
    @GetMapping("/register")
    public String registerPage(
            @RequestParam(value = "error", required = false) String error,
            Model model) {
        if (error != null) {
            model.addAttribute("errorMessage", "이미 존재하는 아이디입니다. 다른 아이디를 사용해 주세요.");
        }
        return "auth/register";
    }

    /**
     * 사용자 등록 처리
     */
    @PostMapping("/register")
    public String registerUser(@RequestParam("username") String username,
            @RequestParam("password") String password,
            RedirectAttributes redirectAttributes) {
        UserVO user = new UserVO();
        user.setUserId(username);
        user.setPassword(passwordEncoder.encode(password));

        boolean registered = userService.append(user);
        if (!registered) {
            redirectAttributes.addAttribute("error", "true");
            return "redirect:/register";
        }

        redirectAttributes.addAttribute("registered", "true");
        return "redirect:/login";
    }

    /**
     * 비밀번호 초기화 요청 페이지
     */
    @GetMapping("/password-reset-request")
    public String passwordResetPage(
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "success", required = false) String success,
            Model model) {
        if (error != null) {
            model.addAttribute("errorMessage", "해당 아이디를 찾을 수 없습니다. 다시 확인해 주세요.");
        } else if (success != null) {
            model.addAttribute("successMessage", "비밀번호 초기화 요청이 접수되었습니다. 관리자가 처리합니다.");
        }
        return "auth/password-reset-request";
    }

    /**
     * 비밀번호 초기화 요청 처리
     */
    @PostMapping("/password-reset-request")
    public String requestPasswordReset(@RequestParam("username") String username,
            RedirectAttributes redirectAttributes) {
        
        UserVO user = userService.itemByUserID(username);
        if (user == null) {
            redirectAttributes.addAttribute("error", "true");
            return "redirect:/password-reset-request";
        } 

        Long seq = user.getSeq();
        boolean updated = userService.setPasswordReset(seq);
        if (!updated) {
            redirectAttributes.addAttribute("error", "true");
            return "redirect:/password-reset-request";
        }

        redirectAttributes.addAttribute("success", "true");
        return "redirect:/login";
    }
}
