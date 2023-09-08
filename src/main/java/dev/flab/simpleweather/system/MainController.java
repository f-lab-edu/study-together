package dev.flab.simpleweather.system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
class MainController {
    @GetMapping("/")
    public String test() {
        return "index.html";
    }
}
