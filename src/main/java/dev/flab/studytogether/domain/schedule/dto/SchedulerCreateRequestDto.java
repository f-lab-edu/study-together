package dev.flab.studytogether.domain.schedule.dto;

import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
public class SchedulerCreateRequestDto {
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
    private String todoContent;

    public SchedulerCreateRequestDto(LocalDate date, String todoContent) {
        this.date = date;
        this.todoContent = todoContent;
    }
}
