package dev.flab.studytogether.domain.schedule.controller;


import dev.flab.studytogether.aop.PostMethodLog;
import dev.flab.studytogether.domain.schedule.SchedulerTodoApiResponse;
import dev.flab.studytogether.domain.schedule.dto.SchedulerCreateRequestDto;
import dev.flab.studytogether.domain.schedule.dto.SchedulerTodoServiceDto;
import dev.flab.studytogether.domain.schedule.dto.TodoRequestDto;
import dev.flab.studytogether.domain.schedule.service.SchedulerTodoService;
import dev.flab.studytogether.utils.SessionUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@Tag(name = "Scheduler", description = "Scheduler/Todo API")
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
    @Operation(summary = "Create Scheduler", description = "스케줄러 생성")
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
    @Operation(summary = "Modify Todo Content", description = "투두리스트 내용 수정")
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
    @Operation(summary = "Delete selected Todo", description = "선택된 투두 리스트 삭제")
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
    @Operation(summary = "Change To-Do List Item Check Status", description = "지정된 투두 아이템의 체크 상태를 변경")
    public SchedulerTodoApiResponse updateToCompleted(TodoRequestDto requestDto, HttpSession httpSession){
        int memberSeqId = SessionUtil.getLoginMemebrSeqId(httpSession);
        return schedulerTodoService.updateToCompleted(new SchedulerTodoServiceDto.Builder()
                .memberSeqId(memberSeqId)
                .todoID(requestDto.getTodoID())
                .schedulerSeq(requestDto.getSchedulerSeq())
                .build());
    }
    @PutMapping("/api/v1/todo/unchecked")
    @Operation(summary = "Change To-Do List Item Check Status", description = "지정된 투두 아이템의 체크 상태를 변경")
    public SchedulerTodoApiResponse updateToUncompleted(TodoRequestDto requestDto, HttpSession httpSession){
        int memberSeqId = SessionUtil.getLoginMemebrSeqId(httpSession);
        return schedulerTodoService.updateToUncomleted(new SchedulerTodoServiceDto.Builder()
                .memberSeqId(memberSeqId)
                .todoID(requestDto.getTodoID())
                .schedulerSeq(requestDto.getSchedulerSeq())
                .build());
    }



}
