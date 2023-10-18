package dev.flab.studytogether.domain.schedule.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
public class SchedulerViewController {

    @GetMapping("/scheduler")
    public String toSchedulerPage(HttpSession session) {
        return "scheduler.html";
    }
}
