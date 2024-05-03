package com.example.narxoz.services;

import com.example.narxoz.models.Lesson;
import com.example.narxoz.models.LessonEnroll;
import com.example.narxoz.models.Student;
import com.example.narxoz.models.StudentStatusLesson;
import com.example.narxoz.repositories.LessonEnrollRepository;
import com.example.narxoz.repositories.LessonRepository;
import com.example.narxoz.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class LessonEnrollService {
    private final LessonEnrollRepository lessonEnrollmentRepository;
    private final UserRepository userRepository;
    private final LessonRepository lessonRepository;

    //when student want to go to lesson
    //lesson student count+1
    public void recordStudentToLesson(Student student, Lesson lesson){
        LessonEnroll lessonEnrollments=new LessonEnroll();
        student.setAttend(student.getAttend()+1);
        lessonEnrollments.setLesson(lesson);
        lesson.setStudent_count(lesson.getStudent_count()+1);
        lessonEnrollments.setStudent(student);
        lessonEnrollments.setStatus(StudentStatusLesson.Active);
        lesson.addLessonEnroll(lessonEnrollments);
        student.addLesson(lessonEnrollments);
        lessonEnrollmentRepository.save(lessonEnrollments);
        log.info("Student:{}  recorded to lesson:{}", userRepository.findById(student.getUser_id()).orElse(null).getFIO(), lesson.getSection().getName());
    }

    //If student cancel the lesson
    //He will just delete in the list Lesson, but in his history it will be there
    public void cancelStudentToLesson(Student student, Lesson lesson){
        lesson.deleteLessonEnroll(lessonEnrollmentRepository.findByLessonAndStudent(lesson, student));
        student.setAttend(student.getAttend()-1);
        lesson.setStudent_count(lesson.getStudent_count()-1);
        log.info("Student:{} canceled lesson:{}", userRepository.findById(student.getUser_id()).orElse(null).getFIO(), lesson.getSection().getName());
    }

}
