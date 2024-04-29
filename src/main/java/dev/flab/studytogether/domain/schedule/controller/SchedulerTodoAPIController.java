package dev.flab.studytogether.domain.schedule.controller;

import dev.flab.studytogether.aop.PostMethodLog;
import dev.flab.studytogether.domain.schedule.SchedulerTodoApiResponse;
import dev.flab.studytogether.domain.schedule.dto.SchedulerCreateRequestDto;
import dev.flab.studytogether.domain.schedule.dto.SchedulerTodoDto;
import dev.flab.studytogether.domain.schedule.dto.TodoRequestDto;
import dev.flab.studytogether.domain.schedule.service.SchedulerTodoService;
import dev.flab.studytogether.utils.SessionUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@AllArgsConstructor
@Tag(name = "Scheduler", description = "Scheduler/Todo API")
public class SchedulerTodoAPIController {

    private final SchedulerTodoService schedulerTodoService;

    @PostMapping("/api/v1/schedulers")
    @PostMethodLog
    @Operation(summary = "Create Scheduler", description = "스케줄러 생성")
    @ResponseStatus(HttpStatus.CREATED)
    public SchedulerTodoApiResponse createScheduler(SchedulerCreateRequestDto requestDto,
                                                    HttpSession httpSession) {
        int memberSequenceId = SessionUtil.getLoginMemberSequenceId(httpSession);

        SchedulerTodoDto schedulerTodoDto = schedulerTodoService.create(SchedulerTodoDto.builder()
                .memberSequenceId(memberSequenceId)
                .todoContent(requestDto.getTodoContent())
                .schedulerDate(requestDto.getDate())
                .build());

        return SchedulerTodoApiResponse.from(schedulerTodoDto);
    }
    @PutMapping("/api/v1/todo")
    @Operation(summary = "Modify Todo Content", description = "투두 내용 수정")
    @ResponseStatus(HttpStatus.OK)
    public SchedulerTodoApiResponse updateTodoContent(TodoRequestDto requestDto,
                                                      HttpSession httpSession) {
        int memberSeqId = SessionUtil.getLoginMemberSequenceId(httpSession);

        SchedulerTodoDto schedulerTodoDto = schedulerTodoService.updateTodoContent(SchedulerTodoDto.builder()
                                                            .memberSequenceId(memberSeqId)
                                                            .schedulerSequenceID(requestDto.getSchedulerSeq())
                                                            .todoSequenceID(requestDto.getTodoID())
                                                            .todoContent(requestDto.getTodoContent())
                                                            .build());

        return SchedulerTodoApiResponse.from(schedulerTodoDto);
    }

    @DeleteMapping("/api/v1/todo/{schedulerSeq}/{todoID}")
    @Operation(summary = "Delete selected Todo", description = "선택된 투두 리스트 삭제")
    @ResponseStatus(HttpStatus.OK)
    public SchedulerTodoApiResponse deleteTodo(@PathVariable int schedulerSeq,
                                               @PathVariable long todoID,
                                               HttpSession httpSession) {
        int memberSeqId = SessionUtil.getLoginMemberSequenceId(httpSession);

        SchedulerTodoDto schedulerTodoDto =
                schedulerTodoService.deleteTodo(SchedulerTodoDto.builder()
                        .memberSequenceId(memberSeqId)
                        .todoSequenceID(todoID)
                        .schedulerSequenceID(schedulerSeq)
                        .build());

        return SchedulerTodoApiResponse.from(schedulerTodoDto);
    }
    @PutMapping("/api/v1/todo/checked")
    @Operation(summary = "Change To-Do List Item Check Status", description = "지정된 투두 아이템의 체크 상태를 변경")
    @ResponseStatus(HttpStatus.OK)
    public SchedulerTodoApiResponse updateToCompleted(TodoRequestDto requestDto, HttpSession httpSession){
        int memberSeqId = SessionUtil.getLoginMemberSequenceId(httpSession);

        SchedulerTodoDto schedulerTodoDto = schedulerTodoService.updateToCompleted(SchedulerTodoDto.builder()
                .memberSequenceId(memberSeqId)
                .todoSequenceID(requestDto.getTodoID())
                .schedulerSequenceID(requestDto.getSchedulerSeq())
                .build());

        return SchedulerTodoApiResponse.from(schedulerTodoDto);
    }
    @PutMapping("/api/v1/todo/unchecked")
    @Operation(summary = "Change To-Do List Item Check Status", description = "지정된 투두 아이템의 체크 상태를 변경")
    @ResponseStatus(HttpStatus.OK)
    public SchedulerTodoApiResponse updateToUncompleted(TodoRequestDto requestDto, HttpSession httpSession){
        int memberSeqId = SessionUtil.getLoginMemberSequenceId(httpSession);

        SchedulerTodoDto schedulerTodoDto = schedulerTodoService.updateToUncompleted(SchedulerTodoDto.builder()
                .memberSequenceId(memberSeqId)
                .todoSequenceID(requestDto.getTodoID())
                .schedulerSequenceID(requestDto.getSchedulerSeq())
                .build());

        return SchedulerTodoApiResponse.from(schedulerTodoDto);
    }
}
