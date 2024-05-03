package com.example.narxoz.controllers;


import com.example.narxoz.services.CoachService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.sql.Date;
import java.util.List;

//@Controller
//@RequiredArgsConstructor
//@RequestMapping("/coach")
//public class CoachController {
//    private final CoachService coachService;
//
//    @GetMapping("/main")
//    public String getCoachSection(Model model, Principal principal){
//        model.addAttribute("sections", coachService.getCoachSection(coachService.getUserByPrincipal(principal)));
//        return "coach_main";
//    }
//
//    @GetMapping("/{section_id}/lessons/{date}")
//    public String getSectionLesson(Model model, Principal principal, @PathVariable Long section_id, @PathVariable Date date){
//        model.addAttribute("lessons", coachService.getCoachLessonByDateAndSection(coachService.getUserByPrincipal(principal), date, section_id));
//        return "coach_lesson";
//    }
//
//    @GetMapping("/{lesson_Id}/all")
//    public String getAllStudentByLesson(Model model, Principal principal, @PathVariable Long lesson_Id){
//        model.addAttribute("students", coachService.getStudentByLesson(lesson_Id));
//        return "coach_student";
//    }
//
//    @PutMapping("/{lesson_id}")
//    public String markAttendance(Model model, @PathVariable Long lesson_id, @RequestParam List<Boolean> attendances){
//        coachService.markAttendance(lesson_id, attendances);
//        return "redirect:main";
//    }
//}
