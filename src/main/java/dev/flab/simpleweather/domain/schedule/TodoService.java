package dev.flab.simpleweather.domain.schedule;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoService {

    private final TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public int create(List<String> todos, Scheduler scheduler){
        todoRepository.createTodo(todos, scheduler);
        return scheduler.getSchedulerSeq();
    }
}
