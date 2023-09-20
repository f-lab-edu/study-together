package dev.flab.simpleweather.domain.schedule;

import org.springframework.stereotype.Repository;

@Repository
public interface TodoRepository {

    int createTodo(String[] todos,Scheduler scheduler);
}
