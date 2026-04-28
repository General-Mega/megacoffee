package com.megacoffee.modules.member;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.megacoffee.security.CustomUserDetails;
import com.megacoffee.utils.DateUtil;
import com.megacoffee.utils.HttpServletUtil;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/members")
public class MemberController {
    private static final Logger logger = LoggerFactory.getLogger(MemberController.class);
    
    @Autowired
    private MemberService service;

    /**
     * 출퇴근 정보 등록 로직 구현
     * @param request
     */ 
    @GetMapping(value = "/commute", produces = MediaType.TEXT_HTML_VALUE)
    public String commute(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies(); // 모든 쿠키 가져오기
        Cookie cookie = null;

        // "seonam" 쿠키 찾기
        if (cookies != null) {
            for (Cookie c : cookies) {
                if ("seonam".equals(c.getName())) {
                    cookie = c;
                }
            }
        }

        // 쿠키가 없는 경우 새로 생성
        if(cookie == null){
            String ID = UUID.randomUUID().toString();
            cookie = new Cookie("seonam", ID);
            cookie.setMaxAge(60*60*24*365); // 1년 동안 유지
            cookie.setPath("/");
            
            response.addCookie(cookie);
        }

        logger.debug("Cookie name: {}", cookie.getName());
        logger.debug("Cookie value: {}", cookie.getValue());
        logger.debug("Cookie domain: {}", cookie.getDomain());
        logger.debug("Cookie path: {}", cookie.getPath());
        logger.debug("Cookie max age: {}", cookie.getMaxAge());


        int maxAge = 60*60*24*30; // 30일
        if(cookie.getMaxAge() <= maxAge){
            logger.debug("Cookie reset up");
            cookie.setMaxAge(60*60*24*365);
            cookie.setPath("/");

            response.addCookie(cookie);
        }
        
        String userAgent = request.getHeader("User-Agent");
        String os = HttpServletUtil.getOS(userAgent);
        String macAddress = HttpServletUtil.getMacAddress(request);
        String clientIP = HttpServletUtil.getClientIP(request);
        String headers = HttpServletUtil.getHeaderDetails(request);

        // 출퇴근 정보 등록
        CommuteVO param = new CommuteVO();
        param.setOs(os);
        param.setMacAddress(macAddress);
        param.setRemoteAddress(clientIP);
        param.setSessionId(cookie.getValue());
        param.setUserAgent(userAgent);
        param.setHeaderDetails(headers);

        // 세션에서 사용자 seq 가져오기
        org.springframework.security.core.Authentication authentication = org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails) {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            param.setUserSeq(userDetails.getSeq());
        }

        String resultCode = service.checkCommute(param);

        String currentDateTime = DateUtil.toStr("yyyy년 MM월 dd일 HH시 mm분 ss초");

        if("100".equals(resultCode)){
            return "<html><head><meta charset=\"UTF-8\"></head><body>"
                + "<script>"
                + "alert('"+currentDateTime+" 출근하셨습니다. 오늘도 잘 부탁 드립니다.');"
                + "try { window.close(); } catch (e) { window.history.back(); }"
                + "</script>"
                + "</body></html>";
        }
        else if("200".equals(resultCode)){
            return "<html><head><meta charset=\"UTF-8\"></head><body>"
                + "<script>"
                + "alert('"+currentDateTime+" 퇴근하셨습니다. 오늘도 수고하셨습니다.');"
                + "try { window.close(); } catch (e) { window.history.back(); }"
                + "</script>"
                + "</body></html>";
        }
        else if("201".equals(resultCode)){
            return "<html><head><meta charset=\"UTF-8\"></head><body>"
                + "<script>"
                + "alert('이미 퇴근 처리되었습니다.' );"
                + "try { window.close(); } catch (e) { window.history.back(); }"
                + "</script>"
                + "</body></html>";
        }
        else{
            return "<html><head><meta charset=\"UTF-8\"></head><body>"
                + "<script>"
                + "alert(' 출퇴근 등록에 실패하였습니다.');"
                + "try { window.close(); } catch (e) { window.history.back(); }"
                + "</script>"
                + "</body></html>";
        }
    }
}
