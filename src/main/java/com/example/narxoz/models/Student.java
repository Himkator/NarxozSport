package com.example.narxoz.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity(name="students2")
@Data
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long student_id;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "student")
    private List<LessonEnroll> lessonEnroll=new ArrayList<>();
    @Column(columnDefinition = "integer default 20")
    private int max_attend;
    @Column(columnDefinition = "integer default 0")
    private int attend;
    private String avatar;
    private Long user_id;


    public void addLesson(LessonEnroll lessonEnrollments){
        lessonEnrollments.setStudent(this);
        lessonEnroll.add(lessonEnrollments);
    }
}

