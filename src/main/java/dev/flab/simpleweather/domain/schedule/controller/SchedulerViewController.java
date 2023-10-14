package dev.flab.simpleweather.domain.schedule.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
public class SchedulerViewController {

    @GetMapping("/scheduler")
    public String toSchedulerPage(HttpSession session) {
        return "scheduler.html";
    }
}
