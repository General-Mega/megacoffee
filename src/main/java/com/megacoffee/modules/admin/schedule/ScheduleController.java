package com.megacoffee.modules.admin.schedule;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.megacoffee.model.ResultVO;
import com.megacoffee.model.SearchVO;
import com.megacoffee.utils.DateUtil;

@Controller
@RequestMapping("/admin/schedule")
public class ScheduleController {
    @Autowired
    private ScheduleService service;

    @GetMapping({"", "/", "/index"})
    public ModelAndView index(@ModelAttribute SearchVO searching, @ModelAttribute ScheduleVO schedule) {
        String startDate = searching.getStartDate();
        String endDate = searching.getEndDate();

        if(startDate == null || startDate.isEmpty()) {
            Date tdate = DateUtil.toDate();
            Date sdate = DateUtil.firstSundayOfMonth(tdate);
            startDate = DateUtil.toStr(sdate);
        }

        if(endDate == null || endDate.isEmpty()) {
            Date tdate = DateUtil.toDate();
            Date edate = DateUtil.lastSaturdayOfMonth(tdate);
            endDate = DateUtil.toStr(edate);
        }

        searching.setStartDate(startDate);
        searching.setEndDate(endDate);

        ModelAndView mav = new ModelAndView("admin/schedule/index");
        mav.addObject("searching", searching);

        return mav;
    }

    @PostMapping("/list")
    public @ResponseBody ResultVO list(@RequestBody SearchVO searching) {
        LocalDateTime startDate = LocalDateTime.parse(searching.getStartDate());
        LocalDateTime endDate = LocalDateTime.parse(searching.getEndDate());

        List<ScheduleVO> list = service.list(searching.getStartDate(), searching.getEndDate());

        ResultVO result = new ResultVO(200, "OK", list);
        
        return result;
    }
}