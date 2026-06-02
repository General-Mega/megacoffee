package com.megacoffee.modules.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.megacoffee.modules.user.UserService;
import com.megacoffee.modules.user.UserVO;

@Controller
public class MainController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 로그인 페이지
     */
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    /**
     * 메인화면
     */
    @GetMapping({"", "/"})
    public ModelAndView main(Model model) {
        return new ModelAndView("redirect:/dashboard");
    }
    /**
     * 대시보드 조회
     */
    @GetMapping("/dashboard")
    public ModelAndView dashboard(Model model) {
        ModelAndView mav = new ModelAndView("dashboard");
        // 대시보드 통계 데이터 설정
        model.addAttribute("totalUsers", 248);
        model.addAttribute("totalMembers", 156);
        model.addAttribute("todayLogins", 42);
        model.addAttribute("passwordResetRequired", 8);
        
        // 이번 달 가입자
        model.addAttribute("monthlySignups", 23);
        
        // 활성 세션
        model.addAttribute("activeSessions", 12);
        
        // 평균 로그인 수
        model.addAttribute("averageDailyLogins", 34);
        
        // 최근 활동 로그
        List<Map<String, Object>> recentActivities = new ArrayList<>();
        
        Map<String, Object> activity1 = new HashMap<>();
        activity1.put("time", "오늘 14:32");
        activity1.put("message", "사용자 'john' 로그인");
        recentActivities.add(activity1);
        
        Map<String, Object> activity2 = new HashMap<>();
        activity2.put("time", "오늘 13:15");
        activity2.put("message", "사용자 'admin' 설정 변경");
        recentActivities.add(activity2);
        
        Map<String, Object> activity3 = new HashMap<>();
        activity3.put("time", "오늘 12:48");
        activity3.put("message", "회원 'park' 가입");
        recentActivities.add(activity3);
        
        Map<String, Object> activity4 = new HashMap<>();
        activity4.put("time", "어제 18:20");
        activity4.put("message", "시스템 백업 완료");
        recentActivities.add(activity4);
        
        Map<String, Object> activity5 = new HashMap<>();
        activity5.put("time", "어제 10:05");
        activity5.put("message", "사용자 'test' 계정 비활성화");
        recentActivities.add(activity5);
        
        model.addAttribute("recentActivities", recentActivities);
        
        return mav;
    }

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