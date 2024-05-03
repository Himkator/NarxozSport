package com.example.narxoz.repositories;

import com.example.narxoz.models.Coach;
import com.example.narxoz.models.Lesson;
import com.example.narxoz.models.Section;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Date;
import java.util.List;

public interface LessonRepository extends JpaRepository<Lesson, Long> {
    List<Lesson> findAllByStartDate(Date date);
    List<Lesson> findAllByCoachAndStartDate(Coach coach, Date date);
    List<Lesson> findAllByCoachAndStartDateAndSection(Coach coach, Date date, Section section);
}
