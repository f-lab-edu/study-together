package dev.flab.studytogether.domain.schedule.repository;

import dev.flab.studytogether.domain.schedule.entity.Scheduler;
import dev.flab.studytogether.domain.schedule.entity.Todo;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TodoRepository {

    Todo save(String todoContent, Scheduler scheduler);
    Optional<Todo> find(int schedulerSeq, long todoID);
    void updateContent(String todoContent, int schedulerSequenceId, long todoID);
    void delete(int schedulerSeq, long todoID);
    void updateCheckStatusToCompleted(int schedulerSeq, long todoID);
    void updateCheckStatusToUncompleted(int schedulerSeq, long todoID);
}
