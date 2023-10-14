package dev.flab.simpleweather.domain.schedule.dto;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class SchedulerCreateRequestDto {
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
    private String todoContent;

    public SchedulerCreateRequestDto(LocalDate date, String todoContent) {
        this.date = date;
        this.todoContent = todoContent;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getTodoContent() {
        return todoContent;
    }
}
