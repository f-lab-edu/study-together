package dev.flab.studytogether.domain.schedule.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class SchedulerTodoDto {
    private int schedulerSequenceID;
    private long todoSequenceID;
    private String todoContent;
    private int memberSequenceId;
    private LocalDate schedulerDate;
}
