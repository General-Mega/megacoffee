package com.megacoffee.modules.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.megacoffee.infra.Security;
import com.megacoffee.model.ResultVO;
import com.megacoffee.modules.admin.user.UserService;
import com.megacoffee.modules.admin.user.UserVO;


@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    /**
     * 로그인 페이지
     */
    @GetMapping("/login")
    public String login() {
        return "admin/login";
    }

    /**
     * 메인화면
     */
    @GetMapping({"", "/"})
    public ModelAndView main(Model model) {
        return new ModelAndView("redirect:/admin/dashboard");
    }
    /**
     * 대시보드 조회
     */
    @GetMapping("/dashboard")
    public ModelAndView dashboard(Model model) {
        ModelAndView mav = new ModelAndView("admin/dashboard");
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
     * 아이디 중복 확인
     * @param user
     * @return
     */
    @PostMapping("/check-id")
    public @ResponseBody ResultVO checkId(@RequestBody UserVO user) {

        String userId = user.getUserId();

        if(userId == null || userId.trim().isEmpty()) {
            ResultVO result = new ResultVO();
            result.setCode(400);
            result.setMessage("아이디를 입력해 주세요.");
            return result;
        }

        UserVO item = userService.itemByUserID(userId);

        if(item == null){
            ResultVO result = new ResultVO();
            result.setCode(200);
            result.setMessage("사용 가능한 아이디입니다.");
            return result;
        } else {
            ResultVO result = new ResultVO();
            result.setCode(409);
            result.setMessage("이미 존재하는 아이디입니다. 다른 아이디를 사용해 주세요.");
            return result;
        }
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
        return "admin/auth/register";
    }
    

    /**
     * 사용자 등록 처리
     */
    @PostMapping("/register")
    public @ResponseBody ResultVO registerUser(@RequestBody UserVO user) {
        String userId = user.getUserId();
        String password = user.getPassword();

        UserVO param = new UserVO();
        param.setUserId(userId);
        param.setPassword(passwordEncoder.encode(password));

        boolean registered = userService.append(param);
        if (registered) {
            try {
                Security.login(userId, password, authenticationManager);
            } catch (Exception ex) {
                ResultVO result = new ResultVO();
                result.setCode(500);
                result.setMessage("사용자 등록은 완료되었으나 자동 로그인에 실패했습니다. 로그인 후 이용해 주세요.");
                return result;
            }

            Map<String, Object> data = new HashMap<>();
            data.put("redirectUrl", "/admin/dashboard");

            ResultVO result = new ResultVO();
            result.setCode(200);
            result.setMessage("사용자 등록이 완료되었습니다.");
            result.setData(data);
            return result;
        } else {
            ResultVO result = new ResultVO();
            result.setCode(500);
            result.setMessage("사용자 등록 중 오류가 발생했습니다. 다시 시도해 주세요.");
            return result;
        }
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
        return "admin/auth/password-reset-request";
    }

    /**
     * 비밀번호 초기화 요청 처리
     */
    @PostMapping("/password-reset-request")
    public @ResponseBody ResultVO passwordResetRequest(@RequestBody UserVO param) {
        String userId = param.getUserId();

        UserVO user = userService.itemByUserID(userId);

        ResultVO result = new ResultVO();
        result.setCode(200);
            result.setMessage("비밀번호 초기화 요청이 접수되었습니다. 관리자가 처리합니다.");

        if (user == null) {
            result.setCode(400);
            result.setMessage("사용자 정보를 확인할 수 없습니다.");
            return result;
        } 

        Long seq = user.getSeq();
        boolean resetRequested = userService.setPasswordReset(seq);
        if(!resetRequested) {
            result.setCode(500);
            result.setMessage("비밀번호 초기화 요청 중 오류가 발생했습니다. 다시 시도해 주세요.");
        }

        return result;
    }
}