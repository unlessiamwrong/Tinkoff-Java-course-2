//package edu.java;
//
//import edu.java.clients.GitHubClient;
//import edu.java.clients.StackOfClient;
//import edu.java.repositories.UserRepository;
//import edu.java.utilities.GetLinkData;
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
//    @Autowired
//    GitHubClient gitHubClient;
//
//    @Autowired StackOfClient stackOfClient;
//    @Autowired
//
//    GetLinkData getLinkData;
//
//    @EventListener(ApplicationReadyEvent.class)
//    public void test() {
//        System.out.println(getLinkData.execute(
//            "https://stackoverflow.com/questions/73097726/spring-boot-cannot-catch-exception-in-controlleradvice"));
//
//    }
//}
