package com.megacoffee.modules.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainController {

    /**
     * 로그인 페이지
     */
    @GetMapping("/login")
    public ModelAndView login(
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "logout", required = false) String logout,
            @RequestParam(value = "registered", required = false) String registered,
            @RequestParam(value = "resetRequested", required = false) String resetRequested,
            Model model) {
        // 템플릿 파일: src/main/resources/templates/login.html
        ModelAndView mav = new ModelAndView("login");
        if (error != null) {
            model.addAttribute("errorMessage", "아이디 또는 비밀번호가 올바르지 않습니다.");
        } else if (logout != null) {
            model.addAttribute("successMessage", "로그아웃 되었습니다.");
        } else if (registered != null) {
            model.addAttribute("successMessage", "회원가입이 완료되었습니다. 로그인해 주세요.");
        } else if (resetRequested != null) {
            model.addAttribute("successMessage", "비밀번호 초기화 요청이 접수되었습니다.");
        }
        return mav;
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
        ModelAndView mav = new ModelAndView("/dashboard");
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
}