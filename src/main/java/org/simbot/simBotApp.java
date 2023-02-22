package org.simbot;


import love.forte.simboot.spring.autoconfigure.EnableSimbot;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableSimbot
@SpringBootApplication
public class simBotApp {
    public static void main(String... args) {
        SpringApplication.run(simBotApp.class,args);
    }
}