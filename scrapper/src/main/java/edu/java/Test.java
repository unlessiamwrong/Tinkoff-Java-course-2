//package edu.java;
//
//import edu.java.domain.User;
//import edu.java.repositories.UserRepository;
//import java.util.List;
//import java.util.Random;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.context.event.ApplicationReadyEvent;
//import org.springframework.context.event.EventListener;
//import org.springframework.stereotype.Component;
//
//@Component
//public class Test {
//
//    @Autowired
//    UserRepository userRepository;
//
//    @EventListener(ApplicationReadyEvent.class)
//    public void test() {
//        User user = new User(new Random().nextLong());
//        userRepository.add(user);
//        List<User> users = userRepository.findAll();
//        System.out.println(users);
//    }
//}
