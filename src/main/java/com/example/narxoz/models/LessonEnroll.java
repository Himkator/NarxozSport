package com.example.narxoz.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity(name="lessonEnrollments2")
@Data
public class LessonEnroll {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn
    private Student student;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn
    private Lesson lesson;

    private StudentStatusLesson status;
    private boolean attended;
}
