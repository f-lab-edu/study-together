package dev.flab.simpleweather.domain.schedule;

import org.springframework.stereotype.Service;

@Service
public class TodoService {

    private final TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public int create(String[] todos, Scheduler scheduler){
        todoRepository.createTodo(todos, scheduler);
        return scheduler.getSchedulerSeq();
    }
}
