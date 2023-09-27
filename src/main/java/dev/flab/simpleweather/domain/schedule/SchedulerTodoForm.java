package dev.flab.simpleweather.domain.schedule;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class SchedulerTodoForm {
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
    private List<String> todos;

    public SchedulerTodoForm(LocalDate date, String[] todos) {
        this.date = date;
        this.todos = Arrays.asList(todos);
    }

    public LocalDate getDate() {
        return date;
    }
    public List<String> getTodos() {
        return todos;
    }
}