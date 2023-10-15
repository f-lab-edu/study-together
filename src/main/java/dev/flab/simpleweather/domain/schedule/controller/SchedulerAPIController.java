package dev.flab.simpleweather.domain.schedule.controller;


import dev.flab.simpleweather.aop.PostMethodLog;
import dev.flab.simpleweather.domain.schedule.SchedulerTodoApiResponse;
import dev.flab.simpleweather.domain.schedule.dto.SchedulerCreateRequestDto;
import dev.flab.simpleweather.domain.schedule.dto.SchedulerTodoServiceDto;
import dev.flab.simpleweather.domain.schedule.dto.TodoRequestDto;
import dev.flab.simpleweather.domain.schedule.service.SchedulerTodoService;
import dev.flab.simpleweather.utils.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
public class SchedulerAPIController {
    private final SchedulerTodoService schedulerTodoService;

    @Autowired
    public SchedulerAPIController(SchedulerTodoService schedulerTodoService) {
        this.schedulerTodoService = schedulerTodoService;
    }


    //스케줄러 생성 API
    @PostMapping("/api/v1/schedulers")
    @ResponseBody
    @PostMethodLog
    public SchedulerTodoApiResponse createScheduler(SchedulerCreateRequestDto requestDto, HttpSession httpSession){
        int memberSeqId = SessionUtil.getLoginMemebrSeqId(httpSession);
        return schedulerTodoService.create(new SchedulerTodoServiceDto.Builder()
                .memberSeqId(memberSeqId)
                .todoContent(requestDto.getTodoContent())
                .localDate(requestDto.getDate())
                .build()
        );
    }
    @PutMapping("/api/v1/todo")
    @ResponseBody
    public SchedulerTodoApiResponse updateTodoContent(TodoRequestDto requestDto, HttpSession httpSession){
        int memberSeqId = SessionUtil.getLoginMemebrSeqId(httpSession);
        return schedulerTodoService.updateTodoContent(new SchedulerTodoServiceDto.Builder()
                                                            .memberSeqId(memberSeqId)
                                                            .schedulerSeq(requestDto.getSchedulerSeq())
                                                            .todoID(requestDto.getTodoID())
                                                            .todoContent(requestDto.getTodoContent())
                                                            .build());
    }

    @DeleteMapping("/api/v1/todo/{schedulerSeq}/{todoID}")
    public SchedulerTodoApiResponse deleteTodo(@PathVariable int schedulerSeq,
                                               @PathVariable long todoID,
                                               HttpSession httpSession){
        int memberSeqId = SessionUtil.getLoginMemebrSeqId(httpSession);
        return schedulerTodoService.deleteTodo(new SchedulerTodoServiceDto.Builder()
                .memberSeqId(memberSeqId)
                .todoID(todoID)
                .schedulerSeq(schedulerSeq)
                .build());
    }
    @PutMapping("/api/v1/todo/checked")
    public SchedulerTodoApiResponse updateToCompleted(TodoRequestDto requestDto, HttpSession httpSession){
        int memberSeqId = SessionUtil.getLoginMemebrSeqId(httpSession);
        return schedulerTodoService.updateToCompleted(new SchedulerTodoServiceDto.Builder()
                .memberSeqId(memberSeqId)
                .todoID(requestDto.getTodoID())
                .schedulerSeq(requestDto.getSchedulerSeq())
                .build());
    }
    @PutMapping("/api/v1/todo/unchecked")
    public SchedulerTodoApiResponse updateToUncomleted(TodoRequestDto requestDto, HttpSession httpSession){
        int memberSeqId = SessionUtil.getLoginMemebrSeqId(httpSession);
        return schedulerTodoService.updateToUncomleted(new SchedulerTodoServiceDto.Builder()
                .memberSeqId(memberSeqId)
                .todoID(requestDto.getTodoID())
                .schedulerSeq(requestDto.getSchedulerSeq())
                .build());
    }



}
