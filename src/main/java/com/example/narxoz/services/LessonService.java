package com.example.narxoz.services;

import com.example.narxoz.models.Lesson;
import com.example.narxoz.repositories.LessonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LessonService {
    private final LessonRepository lessonRepository;

    public List<Lesson> getLessons(){
        return lessonRepository.findAll();
    }

    public Lesson getLesson(Long id){
        return lessonRepository.findById(id).orElse(null);
    }

    public List<Lesson> getLessonsByDate(Date date){
        return lessonRepository.findAllByStartDate(date);
    }

}
