package com.example.narxoz.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity(name="coaches2")
@Data
public class Coach {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long coach_id;

    @OneToMany(mappedBy = "coach")
    private List<Section> sections=new ArrayList<>();

    private String  avatar;

    private Long user_id;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "coach")
    private List<Lesson> lessonList=new ArrayList<>();

    public void addLesson(Lesson lesson){
        lesson.setCoach(this);
        lessonList.add(lesson);
    }
}
