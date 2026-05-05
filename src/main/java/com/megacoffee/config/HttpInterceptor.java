package com.megacoffee.config;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.megacoffee.modules.system.menu.SystemMenuVO;
import com.megacoffee.modules.user.UserVO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class HttpInterceptor implements HandlerInterceptor {
    
    Logger logger = LoggerFactory.getLogger(HttpInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 요청이 컨트롤러에 도달하기 전에 실행되는 로직
        // 예: 인증, 권한 체크, 로깅 등
        logger.info("HTTP 요청 인터셉터: " + request.getRequestURI());
        return true; // true를 반환하면 다음 인터셉터 또는 컨트롤러로 진행
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // 컨트롤러가 실행된 후, 뷰가 렌더링되기 전에 실행되는 로직
        // 예: 모델 데이터 조작, 추가 로깅 등

        HttpSession session = request.getSession();
        List<SystemMenuVO> menus = (List<SystemMenuVO>) session.getAttribute("menus");
        UserVO user = (UserVO) session.getAttribute("user");

        String requestURI = request.getRequestURI();

        logger.info("Menu : " + menus);
        logger.info("User : " + user);
        logger.info("Request URI : " + requestURI);

        if (menus != null) {
            SystemMenuVO pmenu = menus.stream()
                .filter(menu -> menu.getUrl() != null && requestURI.matches(menu.getMatchUrl()))
                .findFirst()
                .orElse(null);
            
            SystemMenuVO cmenu = pmenu == null ? null : pmenu.getChildren().stream()
                .filter(menu -> menu.getUrl() != null && requestURI.matches(menu.getMatchUrl()))
                .findFirst()
                .orElse(null);

            session.setAttribute("pmenu", pmenu);
            session.setAttribute("cmenu", cmenu);
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 뷰가 렌더링된 후에 실행되는 로직
        // 예: 리소스 정리, 예외 처리 등
    }
    
}
