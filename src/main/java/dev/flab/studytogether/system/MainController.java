package dev.flab.studytogether.system;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
class MainController {
    @GetMapping("/")
    public String test() {
        return "index.html";
    }

    @GetMapping("/main")
    public String toMainPage() {
        return "main.html";
    }
}
