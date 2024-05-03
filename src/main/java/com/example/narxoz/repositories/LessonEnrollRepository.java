package com.example.narxoz.repositories;

import com.example.narxoz.models.Lesson;
import com.example.narxoz.models.LessonEnroll;
import com.example.narxoz.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Date;
import java.util.List;

public interface LessonEnrollRepository extends JpaRepository<LessonEnroll, Long> {
    List<LessonEnroll> findAllByStudent(Student student);
    List<LessonEnroll> findAllByStudentAndLesson_StartDate(Student student,Date date);
    LessonEnroll findByLessonAndStudent(Lesson lesson, Student student);

    List<LessonEnroll> findAllByLesson(Lesson lesson);
}
