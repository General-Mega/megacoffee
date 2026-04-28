package com.megacoffee.modules.system;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 관리자 대시보드 및 설정 페이지 컨트롤러
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    /**
     * 대시보드 조회
     */
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
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
        
        return "admin/dashboard";
    }

    /**
     * 설정 페이지 조회
     */
    @GetMapping("/settings")
    public String settings(Model model) {
        // 일반 설정 정보
        model.addAttribute("systemName", "MegaCoffee Management System");
        model.addAttribute("systemDescription", "MegaCoffee 관리 시스템");
        model.addAttribute("itemsPerPage", 20);
        model.addAttribute("language", "ko");
        
        // 보안 설정 정보
        model.addAttribute("passwordMinLength", 8);
        model.addAttribute("passwordExpiryDays", 90);
        model.addAttribute("sessionTimeoutMinutes", 30);
        model.addAttribute("loginAttemptLimit", 5);
        
        // 이메일 설정 정보
        model.addAttribute("smtpServer", "");
        model.addAttribute("smtpPort", "");
        model.addAttribute("senderEmail", "");
        
        // 백업 설정 정보
        model.addAttribute("autoBackupEnabled", true);
        model.addAttribute("backupCycle", "weekly");
        model.addAttribute("backupTime", "02:00");
        model.addAttribute("backupRetentionDays", 30);
        
        return "admin/settings";
    }

    /**
     * 회원 목록 페이지 (placeholder)
     */
    @GetMapping("/member/list")
    public String memberList(Model model) {
        model.addAttribute("memberList", new ArrayList<>());
        model.addAttribute("page", 1);
        model.addAttribute("hasNext", false);
        model.addAttribute("totalPages", 1);
        return "admin/member/list";
    }

    /**
     * 출퇴근 관리 페이지 (placeholder)
     */
    @GetMapping("/member/commute")
    public String commuteManagement(Model model) {
        model.addAttribute("commuteList", new ArrayList<>());
        model.addAttribute("page", 1);
        model.addAttribute("hasNext", false);
        model.addAttribute("totalPages", 1);
        return "admin/member/commute";
    }
}
