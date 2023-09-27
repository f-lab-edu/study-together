package dev.flab.simpleweather.domain.schedule;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoRepository {

    void createTodo(List<String> todos, Scheduler scheduler);
}
