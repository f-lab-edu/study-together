package dev.flab.simpleweather.system;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
class SchedulerController {
    @GetMapping("/scheduler")
    public String initScheduler() {
        return "scheduler.html";
    }


}
