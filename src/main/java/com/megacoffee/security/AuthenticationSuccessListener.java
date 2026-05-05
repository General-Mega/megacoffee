package com.megacoffee.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.megacoffee.modules.system.menu.SystemMenuService;
import com.megacoffee.modules.system.menu.SystemMenuVO;
import com.megacoffee.modules.user.UserService;
import com.megacoffee.modules.user.UserVO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Component
public class AuthenticationSuccessListener {

    @Autowired
    private UserService userService;

    @Autowired
    private SystemMenuService menuService;

    @EventListener
    public void onAuthenticationSuccess(AuthenticationSuccessEvent event) {
        String userId = event.getAuthentication().getName();

        UserVO user = userService.itemByUserID(userId);
        if (user == null) {
            return;
        }   

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        session.setAttribute("user", user);

        Long userSeq = user.getSeq();
        userService.updateLastLogin(userSeq);

        //메뉴 정보 조회
        List<SystemMenuVO> menus = menuService.listByUserSeq(userSeq);
        session.setAttribute("menus", menus);


    }
}
