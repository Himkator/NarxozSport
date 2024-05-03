package com.example.narxoz.services;

import com.example.narxoz.models.Coach;
import com.example.narxoz.models.Student;
import com.example.narxoz.models.User;
import com.example.narxoz.repositories.CoachRepository;
import com.example.narxoz.repositories.StudentRepository;
import com.example.narxoz.repositories.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final StudentRepository studentRepository;
    private final CoachRepository coachRepository;

    private final JavaMailSender javaMailSender;
    @Value("${default.user.image}")
    private String defaultUserImage;

    //Create User, but firstly understand who is he, student/teacher
    public boolean createUser(User user) throws MessagingException, UnsupportedEncodingException {
        if(userRepository.findBySkFk(user.getSkFk())!=null) return false;
        Random random=new Random();
        ArrayList<Integer> numbers = new ArrayList<>();

        for(int i=0;i<4;i++){
            numbers.add(random.nextInt(10));
        }
        String text=numbers.stream().map(nums->nums+"").collect(Collectors.joining());
        if(user.getUsername().charAt(0)=='S'){
            Student student=new Student();
            student.setUser_id(user.getId());
            student.setAvatar(defaultUserImage);
            user.setStudent(student);
            log.info("Student was created, student:{}", user.getSkFk());
            studentRepository.save(student);
        }
        else if(user.getSkFk().charAt(0)=='F'){
            Coach coach=new Coach();
            coach.setUser_id(user.getId());
            coach.setAvatar(defaultUserImage);
            user.setCoach(coach);
            log.info("Coach was created:{}", user.getSkFk());
            coachRepository.save(coach);
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setVerificationCode(text);
        user.setEnabled(false);
        userRepository.save(user);
        sendVerificationEmail(user);
        return true;
    }

    public User getUserByPrincipal(Principal principal) {
        if (principal == null) return new User();
        return userRepository.findBySkFk(principal.getName());
    }

    //when user register he need to be verify
    private void sendVerificationEmail(User user) throws MessagingException, UnsupportedEncodingException {
        String toAddress = user.getMail();
        String fromAddress = "nurymsejtkazy@gmail.com";
        String senderName = "Narxoz";
        String subject = "Please verify your registration";
        String content = "Dear "+user.getFIO()+",<br>"
                + "Please click the link below to verify your registration:<br>"
                + user.getVerificationCode()
                + "<br>Thank you,<br>";

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);
        helper.setText(content, true);

        javaMailSender.send(message);
    }

    public boolean verify(String code, String SkFk){
        User user=userRepository.findBySkFk(SkFk);
        if(user == null || user.isEnabled()){
            return false;
        }
        if(user.getVerificationCode().equals(code)){
            user.setVerificationCode(null);
            user.setEnabled(true);
            userRepository.save(user);
            return  true;
        }
        return false;
    }
    //if he forget password
    public boolean forget(String SkFk) throws MessagingException, UnsupportedEncodingException {
        User user=userRepository.findBySkFk(SkFk);
        if(user!=null){
            Random random=new Random();
            ArrayList<Integer> numbers = new ArrayList<>();

            for(int i=0;i<4;i++){
                numbers.add(random.nextInt(10));
            }
            String text=numbers.stream().map(nums->nums+"").collect(Collectors.joining());
            user.setPassword(passwordEncoder.encode(text));
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);
            String toAddress = user.getMail();
            String fromAddress = "nurymsejtkazy@gmail.com";
            String senderName = "Narxoz";
            String subject = "Please verify your registration";
            String content = "Dear [[name]],<br>"
                    + "Your new password(don't forget change it):<br>"
                    + "[[code]]"
                    + "Thank you,<br>";
            helper.setFrom(fromAddress, senderName);
            helper.setTo(toAddress);
            helper.setSubject(subject);

            content = content.replace("[[name]]", user.getFIO());

            content = content.replace("[[code]]", user.getPassword());

            helper.setText(content, true);
            userRepository.save(user);
            return true;
        }
        return false;
    }
}
