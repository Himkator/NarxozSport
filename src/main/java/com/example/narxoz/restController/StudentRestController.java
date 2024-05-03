package com.example.narxoz.restController;

import com.example.narxoz.models.Student;
import com.example.narxoz.services.LessonEnrollService;
import com.example.narxoz.services.LessonService;
import com.example.narxoz.services.StudentService;
import com.example.narxoz.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.sql.Date;

@RestController
@RequestMapping("/student")
@RequiredArgsConstructor
public class StudentRestController {
    private final StudentService studentService;
    private final LessonService lessonService;
    private final LessonEnrollService lessonEnrollService;
    private final UserService userService;

    @GetMapping("/lessons/{date}")
    public ResponseEntity<?> getAllLessons(@PathVariable Date date) {
        return ResponseEntity.ok(lessonService.getLessonsByDate(date));
    }

    @PostMapping("/lessons/{lessonId}/record")
    public ResponseEntity<?> recordLesson(@PathVariable Long lessonId, Principal principal) {
        lessonEnrollService.recordStudentToLesson(studentService.getByPrincipal(principal), lessonService.getLesson(lessonId));
        return ResponseEntity.ok("Student successfully recorded to lesson");
    }

    @PutMapping("/lessons/{lessonId}/cancel")
    public ResponseEntity<?> cancelLesson(@PathVariable Long lessonId, Principal principal) {
        lessonEnrollService.cancelStudentToLesson(studentService.getByPrincipal(principal), lessonService.getLesson(lessonId));
        return ResponseEntity.ok("Student successfully canceled lesson");
    }

    @GetMapping("/lessons/history")
    public ResponseEntity<?> getLessonHistory(Principal principal) {
        return ResponseEntity.ok(studentService.getStudentLesson(studentService.getByPrincipal(principal)));
    }

    @GetMapping("/lessons/student-history/{date}")
    public ResponseEntity<?> getLessonByDate(@PathVariable Date date, Principal principal) {
        return ResponseEntity.ok(studentService.getStudentLessonById(studentService.getByPrincipal(principal), date));
    }

    @GetMapping("/profile")
    public ResponseEntity<Student> getByPrincipal(Principal principal){
        Student student=studentService.getByPrincipal(principal);
        return ResponseEntity.ok(student);
    }
    @PutMapping("/edit/profile")
    public ResponseEntity<String> editProfile(Principal principal, @RequestParam String photoUrl){
        studentService.editProfile(userService.getUserByPrincipal(principal), photoUrl);
        return ResponseEntity.ok("Profile succesfully was edited");
    }
}
