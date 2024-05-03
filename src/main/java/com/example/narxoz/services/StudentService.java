package com.example.narxoz.services;

import com.example.narxoz.models.*;
import com.example.narxoz.repositories.LessonEnrollRepository;
import com.example.narxoz.repositories.StudentRepository;
import com.example.narxoz.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class StudentService {
    private final StudentRepository studentRepository;
    private final LessonEnrollRepository lessonEnrollRepository;
    private final UserService userService;
    private final UserRepository userRepository;


    //Student all history
    public List<LessonEnroll> getStudentLesson(Student student){
        log.info("Student's lesson was sent, student:{}", userRepository.findById(student.getUser_id()).orElse(null).getSkFk());
        return lessonEnrollRepository.findAllByStudent(student);
    }
    //Student all lesson by date but they are recorded
    public List<LessonEnroll> getStudentLessonById(Student student, Date date){
        log.info("Student's lesson by date was sent, student:{}", userRepository.findById(student.getUser_id()).orElse(null).getSkFk());
        List<LessonEnroll> lessonEnrolls=lessonEnrollRepository.findAllByStudentAndLesson_StartDate(student, date);
        for(LessonEnroll lessonEnroll:lessonEnrolls){
            if(lessonEnroll.getStatus().equals(StudentStatusLesson.cancel)) lessonEnrolls.remove(lessonEnroll);
        }
        return lessonEnrolls;
    }

    public Student getByPrincipal(Principal principal){
        return userService.getUserByPrincipal(principal).getStudent();
    }

    public void editProfile(User user, String photoUrl){
        User user1=userRepository.findBySkFk(user.getSkFk());
        user1.setFIO(user.getFIO());
        user1.setPassword(user.getPassword());
        user1.setMail(user.getMail());
        user1.getStudent().setAvatar(photoUrl);
        userRepository.save(user1);
    }
}
