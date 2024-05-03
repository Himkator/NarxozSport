package com.example.narxoz.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity(name="sections2")
@Data
public class Section {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long section_id;
    private String name;
    private String description;

    @OneToMany(mappedBy = "section")
    private List<Lesson> lessons=new ArrayList<>();

    @ManyToOne
    @JoinColumn
    private Coach coach;

    public void addLesson(Lesson lesson){
        lesson.setSection(this);
        lessons.add(lesson);
    }
}
