package dev.jotxee.springboot;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EanController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello World!";
    }
}
