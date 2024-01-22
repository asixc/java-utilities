package dev.jotxee;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"dev.jotxee.springboot"})
public class MainSpringBootApp {
    private static final Logger log = LoggerFactory.getLogger(MainSpringBootApp.class);

    public static void main(String[] args) {
        SpringApplication.run(MainSpringBootApp.class, args);
    }
}
