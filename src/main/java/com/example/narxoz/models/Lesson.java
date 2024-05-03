package com.example.narxoz.models;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity(name="lessons2")
@Data
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long lesson_id;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn
    private Section section;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn
    private Coach coach;

    private Date startDate;
    private Date endDate;
    private String location;
    private int max_student;
    @Column(columnDefinition = "integer default 0")
    private int student_count;
    private boolean LFK;


    @OneToMany(mappedBy = "lesson")
    private List<LessonEnroll> lessonEnrollments=new ArrayList<>();
    public void addLessonEnroll(LessonEnroll lessonEnroll){
        lessonEnroll.setLesson(this);
        lessonEnrollments.add(lessonEnroll);
    }
    public void deleteLessonEnroll(LessonEnroll lessonEnroll){
        lessonEnrollments.remove(lessonEnroll);
    }
}