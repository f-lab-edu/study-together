package dev.flab.studytogether.domain.schedule.controller;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@Hidden
public class SchedulerViewController {

    @GetMapping("/scheduler")
    public String toSchedulerPage(HttpSession session) {
        return "scheduler.html";
    }
}
