package dev.flab.simpleweather.domain.schedule;

import dev.flab.simpleweather.system.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.nio.charset.Charset;
import java.util.Arrays;

@Controller
public class SchedulerController {
    private final SchedulerService schedulerService;
    private final TodoService todoService;

    @Autowired
    public SchedulerController(SchedulerService schedulerService, TodoService todoService) {
        this.schedulerService = schedulerService;
        this.todoService = todoService;
    }

    @GetMapping("/scheduler")
    @ResponseBody
    public String toSchedulerPage(HttpSession session) {

        return "scheduler.html";
    }

    //스케줄러 생성 API
    @PostMapping("/api/v1/schedulers")
    @ResponseBody
    public ResponseEntity<Message> create(HttpServletRequest request, HttpSession httpSession){
        String date = request.getParameter("date");
        String[] todos = request.getParameterValues("todos");
        Scheduler scheduler = new Scheduler(httpSession, date);

        schedulerService.create(scheduler);

        todoService.create(todos, scheduler);


        Message message = new Message();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        message.setStatus(Message.StatusEnum.OK);
        message.setData(todos);

        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
