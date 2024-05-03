package com.example.narxoz.services;

import com.example.narxoz.models.*;
import com.example.narxoz.repositories.*;
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
public class CoachService {
    private final CoachRepository coachRepository;
    private final LessonRepository lessonRepository;
    private final LessonEnrollRepository lessonEnrollRepository;
    private final StudentRepository studentRepository;
    private final SectionRepository sectionRepository;
    private final UserService userService;
    private final UserRepository userRepository;

    public void createCoach(User user){
        Coach coach=new Coach();
        coach.setUser_id(user.getId());
        log.info("Coach was created:{}", user.getSkFk());
        coachRepository.save(coach);

    }

    public List<Lesson> getCoachLessonByDate(Coach coach, Date date){
        log.info("All coach lesson by date was sent coach:{}", userRepository.findById(coach.getUser_id()).orElse(null).getSkFk());
        return lessonRepository.findAllByCoachAndStartDate(coach, date);
    }

    public List<Lesson> getCoachLessonByDateAndSection(Coach coach, Date date, Long section_id){
        Section section=sectionRepository.findById(section_id).orElse(null);
        log.info("All coach lesson by date and section was sent coach:{}", userRepository.findById(coach.getUser_id()).orElse(null).getSkFk());
        return lessonRepository.findAllByCoachAndStartDateAndSection(coach, date, section);
    }

    public List<Section> getCoachSection(Coach coach){
        log.info("All coach section was sent coasch:{}", userRepository.findById(coach.getUser_id()).orElse(null).getSkFk());
        return coach.getSections();
    }

    public Coach getUserByPrincipal(Principal principal) {
        return userService.getUserByPrincipal(principal).getCoach();
    }

    public List<Student> getStudentByLesson(Long lesson_id){
        List<Student> students=new ArrayList<>();
        log.info("All coach lesson's student was sent lesson:{}", lessonRepository.findById(lesson_id).orElse(null).getSection().getName());
        List<LessonEnroll> lessonEnrolls=lessonEnrollRepository.findAllByLesson(lessonRepository.findById(lesson_id).orElse(null));
        for(LessonEnroll lessonEnroll:lessonEnrolls){
            students.add(lessonEnroll.getStudent());
        }
        students.sort((Student a, Student b)->userRepository.findById(a.getUser_id()).orElse(null).getFIO().compareTo(userRepository.findById(b.getUser_id()).orElse(null).getFIO()));
        return students;
    }

    public void markAttendance(Long lesson_id, List<Boolean> attendance){
        List<Student> students=getStudentByLesson(lesson_id);
        students.sort((Student a, Student b)->userRepository.findById(a.getUser_id()).orElse(null).getFIO().compareTo(userRepository.findById(b.getUser_id()).orElse(null).getFIO()));
        int i=0;
        log.info("Lesson attendance was recorded, lesson:{}", lessonRepository.findById(lesson_id).orElse(null).getSection().getName());
        for (Student student:students){
            getLesson(lesson_id, student.getLessonEnroll()).setAttended(attendance.get(i));
            i+=1;
        }
    }

    private LessonEnroll getLesson(Long lesson_id, List<LessonEnroll> lessonEnrolls){
        for (LessonEnroll lessonEnroll:lessonEnrolls){
            if(lessonEnrollRepository.findById(lesson_id).orElse(null).equals(lessonEnroll.getLesson())){
                return lessonEnroll;
            }
        }
        return null;
    }
    public void editProfile(User user, String photo){
        User user1=userRepository.findBySkFk(user.getSkFk());
        user1.setFIO(user.getFIO());
        user1.setPassword(user.getPassword());
        user1.setMail(user.getMail());
        user1.getCoach().setAvatar(photo);
        userRepository.save(user1);
    }

}
