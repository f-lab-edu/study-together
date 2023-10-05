package dev.flab.simpleweather.domain.schedule;


import dev.flab.simpleweather.aop.PostMethodLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Controller
public class SchedulerController {
    private final SchedulerTodoService schedulerTodoService;

    @Autowired
    public SchedulerController(SchedulerTodoService schedulerTodoService) {
        this.schedulerTodoService = schedulerTodoService;
    }




    //스케줄러 생성 API
    @PostMapping("/api/v1/schedulers")
    @ResponseBody
    public SchedulerApiResponse create(@RequestParam String date,@RequestParam List<String> todos,
    HttpSession httpSession){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(date, formatter);

        int seqId = (int)httpSession.getAttribute("seq_id");
        String id = httpSession.getAttribute("id").toString();

        return schedulerTodoService.create(seqId, id, localDate, todos);
    }
}
