package dev.flab.studytogether.domain.schedule.service;

import dev.flab.studytogether.domain.schedule.dto.SchedulerTodoServiceDto;
import dev.flab.studytogether.domain.schedule.entity.Scheduler;
import dev.flab.studytogether.domain.schedule.SchedulerTodoApiResponse;
import dev.flab.studytogether.domain.schedule.entity.Todo;
import dev.flab.studytogether.domain.schedule.repository.SchedulerRepository;
import dev.flab.studytogether.domain.schedule.repository.TodoRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SchedulerTodoService {
    private final SchedulerRepository schedulerRepository;
    private final TodoRepository todoRepository;

    public SchedulerTodoService(SchedulerRepository schedulerRepository, TodoRepository todoRepository) {
        this.schedulerRepository = schedulerRepository;
        this.todoRepository = todoRepository;
    }


    public SchedulerTodoApiResponse create(SchedulerTodoServiceDto serviceDto) {

        //seqId와 date로 해당 스케줄러 존재하는지 확인
        Optional<Scheduler> result = schedulerRepository.find(serviceDto.getMemberSeqId(), serviceDto.getLocalDate());
        Scheduler scheduler;
        //스케줄러 존재하지 않는다면 새로운 스케줄러 생성
        if(result.isEmpty()){
            scheduler = schedulerRepository.createScheduler(serviceDto.getLocalDate(), serviceDto.getMemberSeqId());
        }else{ //스케줄러 존재하면 기존 스케줄러에 todo 추가
            scheduler = result.get();
        }
        Todo todo = todoRepository.save(serviceDto.getTodoContent(), scheduler);
        return new SchedulerTodoApiResponse(todo.getSchedulerSeq(), todo.getTodoID(), todo.getTodoContent());
    }

    public SchedulerTodoApiResponse updateTodoContent(SchedulerTodoServiceDto serviceDto){
        todoRepository.updateContent(serviceDto.getTodoContent(), serviceDto.getSchedulerSeq(), serviceDto.getTodoID());
        return new SchedulerTodoApiResponse(serviceDto.getSchedulerSeq(), serviceDto.getTodoID(), serviceDto.getTodoContent());

    }

    public SchedulerTodoApiResponse deleteTodo(SchedulerTodoServiceDto serviceDto){
        todoRepository.delete(serviceDto.getSchedulerSeq(), serviceDto.getTodoID());
        return new SchedulerTodoApiResponse(serviceDto.getSchedulerSeq(), serviceDto.getTodoID(), serviceDto.getTodoContent());
    }

    public SchedulerTodoApiResponse updateToCompleted(SchedulerTodoServiceDto serviceDto){
        todoRepository.updateCheckStatusToCompleted(serviceDto.getSchedulerSeq(), serviceDto.getTodoID());
        return new SchedulerTodoApiResponse(serviceDto.getSchedulerSeq(), serviceDto.getTodoID(), serviceDto.getTodoContent());
    }

    public SchedulerTodoApiResponse updateToUncomleted(SchedulerTodoServiceDto serviceDto){
        todoRepository.updateCheckStatusToUncompleted(serviceDto.getSchedulerSeq(), serviceDto.getTodoID());
        return new SchedulerTodoApiResponse(serviceDto.getSchedulerSeq(), serviceDto.getTodoID(), serviceDto.getTodoContent());
    }
}

