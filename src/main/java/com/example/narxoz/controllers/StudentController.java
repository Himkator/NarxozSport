package com.example.narxoz.controllers;


import com.example.narxoz.models.Lesson;
import com.example.narxoz.services.LessonEnrollService;
import com.example.narxoz.services.LessonService;
import com.example.narxoz.services.StudentService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.sql.Date;

//@Controller
//@RequestMapping("/student")
//@RequiredArgsConstructor
//public class StudentController {
//    private final StudentService studentService;
//    private final LessonService lessonService;
//    private final LessonEnrollService lessonEnrollService;
//
//    @GetMapping("/main/{date}")
//    public String getAllLesson(Model model, Principal principal, @PathVariable Date date){
//        model.addAttribute("lessons", lessonService.getLessonsByDate(date));
//        return "student_main";
//    }
//
//    @PostMapping("/main/record/{lesson_id}")
//    public String recordLesson(Model model, Principal principal, @PathVariable Long lesson_id){
//        lessonEnrollService.recordStudentToLesson(studentService.getByPrincipal(principal), lessonService.getLesson(lesson_id));
//        return "redirect:/main";
//    }
//
//    @PutMapping("/main/cancel/{lesson_id}")
//    public String cancelLesson(Model model, Principal principal, @PathVariable Long lesson_id){
//        lessonEnrollService.cancelStudentToLesson(studentService.getByPrincipal(principal), lessonService.getLesson(lesson_id));
//        return "redirect:/main";
//    }
//
//    @GetMapping("/history")
//    public String getAllHistory(Model model, Principal principal){
//        model.addAttribute("lessons-history",studentService.getStudentLesson(studentService.getByPrincipal(principal)));
//        return "history";
//    }
//
//    @GetMapping("/student-lesson/{date}")
//    public String getStudentLessonByDate(Model model, Principal principal, @PathVariable Date date){
//        model.addAttribute("lessons",studentService.getStudentLessonById(studentService.getByPrincipal(principal), date));
//        return "student-history";
//    }
//}
