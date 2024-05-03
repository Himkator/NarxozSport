package com.example.narxoz.restController;


import com.example.narxoz.models.Coach;
import com.example.narxoz.services.CoachService;
import com.example.narxoz.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.sql.Date;
import java.util.List;

@RestController
@RequestMapping("/coach")
@RequiredArgsConstructor
public class CoachRestController {
    private final CoachService coachService;
    private final UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<Coach> getByPrincipal(Principal principal){
        Coach coach=coachService.getUserByPrincipal(principal);
        return ResponseEntity.ok(coach);
    }

    @GetMapping("/sections")
    public ResponseEntity<?> getCoachSections(Principal principal) {
        return ResponseEntity.ok(coachService.getCoachSection(coachService.getUserByPrincipal(principal)));
    }

    @GetMapping("/sections/{sectionId}/lessons/{date}")
    public ResponseEntity<?> getSectionLesson(@PathVariable Long sectionId, @PathVariable Date date, Principal principal) {
        return ResponseEntity.ok(coachService.getCoachLessonByDateAndSection(coachService.getUserByPrincipal(principal), date, sectionId));
    }

    @GetMapping("/lessons/{lessonId}/students")
    public ResponseEntity<?> getAllStudentByLesson(@PathVariable Long lessonId) {
        return ResponseEntity.ok(coachService.getStudentByLesson(lessonId));
    }

    @PutMapping("/lessons/{lessonId}/attendance")
    public ResponseEntity<?> markAttendance(@PathVariable Long lessonId, @RequestBody List<Boolean> attendances) {
        coachService.markAttendance(lessonId, attendances);
        return ResponseEntity.ok("Attendance marked successfully");
    }

    @PutMapping("/edit/profile")
    public ResponseEntity<String> editProfile(Principal principal, @RequestParam String photo){
        coachService.editProfile(userService.getUserByPrincipal(principal), photo);
        return ResponseEntity.ok("Profile succesfully was edited");
    }
}
