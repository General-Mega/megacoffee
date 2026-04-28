package com.megacoffee.modules.system.auth;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.megacoffee.model.SearchVO;
import com.megacoffee.security.CustomUserDetails;

@Controller
@RequestMapping("/system/auth")
public class AuthController {
    @Autowired
    private AuthService service;

    @GetMapping({"", "/", "/list"})
    public ModelAndView index(SearchVO param) {
        if (param.getPage() < 1) {
            param.setPage(1);
        }
        if (param.getSize() <= 0) {
            param.setSize(10);
        }

        int totalCount = service.count(param);
        int totalPages = totalCount == 0 ? 1 : (int) Math.ceil((double) totalCount / param.getSize());
        if (param.getPage() > totalPages) {
            param.setPage(totalPages);
        }

        List<AuthVO> authList = service.list(param);
        int visiblePages = 10;
        int pageStart = 1;
        int pageEnd = Math.min(visiblePages, totalPages);
        if (totalPages > visiblePages) {
            int half = visiblePages / 2;
            pageStart = Math.max(1, param.getPage() - half);
            pageEnd = Math.min(totalPages, pageStart + visiblePages - 1);
            pageStart = Math.max(1, pageEnd - visiblePages + 1);
        }

        ModelAndView mav = new ModelAndView("system/auth/list");
        mav.addObject("searching", param);
        mav.addObject("authList", authList);
        mav.addObject("page", param.getPage());
        mav.addObject("totalCount", totalCount);
        mav.addObject("totalPages", totalPages);
        mav.addObject("pageStart", pageStart);
        mav.addObject("pageEnd", pageEnd);
        mav.addObject("hasNext", param.getPage() < totalPages);
        mav.addObject("hasPrevious", param.getPage() > 1);

        return mav;
    }

    @PostMapping("/save")
    public String save(AuthVO auth, SearchVO search, RedirectAttributes ra) {
        Long userSeq = getCurrentUserSeq();
        if (auth.getSeq() > 0) {
            service.modify(auth, userSeq);
        } else {
            service.append(auth, userSeq);
        }

        ra.addAttribute("page", search.getPage());
        ra.addAttribute("searchValue", search.getSearchValue());
        return "redirect:/system/auth";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam(name = "seqs", required = false) List<Integer> seqs, SearchVO search, RedirectAttributes ra) {
        if (seqs != null && !seqs.isEmpty()) {
            service.remove(seqs, getCurrentUserSeq());
        }

        ra.addAttribute("page", search.getPage());
        ra.addAttribute("searchValue", search.getSearchValue());
        return "redirect:/system/auth";
    }

    private Long getCurrentUserSeq() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails) {
            return ((CustomUserDetails) authentication.getPrincipal()).getSeq();
        }
        return 0L;
    }
}
