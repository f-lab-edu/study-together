package dev.flab.studytogether.domain.schedule.service;

import dev.flab.studytogether.domain.schedule.entity.Scheduler;
import dev.flab.studytogether.domain.schedule.repository.TodoRepository;
import org.springframework.stereotype.Service;

@Service
public class TodoService {

    private final TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public int create(String todoContent, Scheduler scheduler){
        todoRepository.save(todoContent, scheduler);
        return scheduler.getSchedulerSeq();
    }
}
