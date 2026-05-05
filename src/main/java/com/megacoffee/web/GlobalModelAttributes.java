package com.megacoffee.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.megacoffee.modules.system.menu.SystemMenuService;
import com.megacoffee.modules.system.menu.SystemMenuVO;
import com.megacoffee.modules.user.UserService;
import com.megacoffee.modules.user.UserVO;
import com.megacoffee.security.CustomUserDetails;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@ControllerAdvice
public class GlobalModelAttributes {

    @Autowired
    private UserService userService;

    @Autowired
    private SystemMenuService menuService;

    @ModelAttribute("user")
    public UserVO addCurrentUser(HttpServletRequest request, Authentication authentication) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            Object sessionUser = session.getAttribute("user");
            if (sessionUser instanceof UserVO) {
                return (UserVO) sessionUser;
            }
        }

        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails) {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            UserVO user = userService.itemByUserID(userDetails.getUsername());
            if (user != null) {
                request.getSession(true).setAttribute("user", user);
                return user;
            }
        }

        return null;
    }

    @ModelAttribute("menus")
    public List<SystemMenuVO> addCurrentMenus(HttpServletRequest request, Authentication authentication) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            Object sessionMenus = session.getAttribute("menus");
            if (sessionMenus instanceof List) {
                @SuppressWarnings("unchecked")
                List<SystemMenuVO> menus = (List<SystemMenuVO>) sessionMenus;
                return menus;
            }
        }

        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails) {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            if (userDetails.getSeq() != null) {
                List<SystemMenuVO> menus = menuService.listByUserSeq(userDetails.getSeq());
                request.getSession(true).setAttribute("menus", menus);
                return menus;
            }
        }

        return null;
    }
}
