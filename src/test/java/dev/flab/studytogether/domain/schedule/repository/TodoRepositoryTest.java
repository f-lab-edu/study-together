package dev.flab.studytogether.domain.schedule.repository;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

import dev.flab.studytogether.domain.schedule.entity.Scheduler;
import dev.flab.studytogether.domain.schedule.entity.Todo;
import dev.flab.studytogether.enums.CompleteStatus;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.util.Optional;


@JdbcTest
@Sql({"classpath:schema.sql", "classpath:test-data.sql"})
class TodoRepositoryTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private TodoRepository todoRepository;
    private Scheduler scheduler;

    @BeforeEach
    void setUp() {
        this.todoRepository = new TodoRepositoryImpl(jdbcTemplate);
        this.scheduler = new Scheduler(1, LocalDate.of(2024, 5, 1), 1);
    }

    @Test
    @DisplayName("Todo를 save 하면 Todo Sequence Id를 포함한 Todo를 반환한다.")
    void givenTodoContentAndScheduler_WhenTodoIsSaved_ThenReturnsTodoWithSequenceId() {
        //given
        String todoContent = "test content";

        //when
        Todo savedTodo = todoRepository.save(todoContent, scheduler);

        //then
        assertThat(savedTodo.getSchedulerSeq()).isPositive();
    }

    @Test
    @DisplayName("Todo를 save 하면 Todo 정보가 올바르게 저장되는지 확인.")
    void givenTodoContentAndScheduler_ShouldSaveTodoWithCorrectDetails() {
        //given
        String todoContent = "test content";

        //when
        Todo savedTodo = todoRepository.save(todoContent, scheduler);

        //then
        assertEquals(todoContent, savedTodo.getTodoContent());
        assertEquals(scheduler.getSchedulerSeq(), savedTodo.getSchedulerSeq());
    }
    
    @Test
    @DisplayName("데이터베이스에 존재하는 Scheduler Sequence Id와 Todo Sequence Id로 find 하면 존재하는 Todo를 반환한다.")
    void whenFindTodoByExistingSchedulerSequenceIdAndTodoSequenceId_ThenReturnsOptionalOfExistingTodo() {
        //given
        int schedulerSequenceId = scheduler.getSchedulerSeq();
        long todoSequenceId = 1;
        
        //when
        Optional<Todo> todo = todoRepository.find(schedulerSequenceId, todoSequenceId);
        
        //then
        assertThat(todo).isPresent();
    }

    @Test
    @DisplayName("데이터베이스에 존재하지 않는 Scheduler Sequence Id와 Todo Sequence Id로 find 하면 Optional Empty를 반환한다.")
    void whenFindTodoByNonExistingSchedulerSequenceIdAndTodoSequenceId_ThenReturnsOptionalEmpty() {
        //given
        int schedulerSequenceId = 230;
        long todoId = 1;

        //when
        Optional<Todo> todo = todoRepository.find(schedulerSequenceId, todoId);

        //then
        assertThat(todo).isEmpty();
    }

    @Test
    @DisplayName("Todo의 content 속성을 업데이트하고 변경사항이 올바르게 저장됐는지 확인한다.")
    void whenTodoContentIsUpdated_thenChangesShouldBeCorrectlyStored() {
        //given
        String updateTodoContent = "test update content";
        int schedulerSequenceId = scheduler.getSchedulerSeq();
        long todoSequenceId = 1;

        //when
        todoRepository.updateContent(updateTodoContent, schedulerSequenceId, todoSequenceId);

        //then
        Todo todo = todoRepository.find(schedulerSequenceId, todoSequenceId)
                .orElseThrow(IllegalArgumentException::new);
        assertEquals(updateTodoContent, todo.getTodoContent());

    }
    
    @Test
    @DisplayName("Todo 삭제 시, 해당 Todo를 find하면 존재하지 않아 Optional Empty를 반환한다.")
    void whenTodoIsDeleted_thenShouldBeNotFound() {
        //given
        int schedulerSequenceId = scheduler.getSchedulerSeq();
        long todoSequenceId = 1;

        //when
        todoRepository.delete(schedulerSequenceId, todoSequenceId);
        
        //then
        Optional<Todo> todo = todoRepository.find(schedulerSequenceId, todoSequenceId);
        assertThat(todo).isEmpty();
    }

    @Test
    @DisplayName("Todo 완료 상태를 Completed로 변경하면 업데이트가 되어야 한다.")
    void whenTodoCheckStatusIsUpdatedToCompleted_thenTodoCompleteStatusShouldBeUpdated() {
        //given
        int schedulerSequenceId = scheduler.getSchedulerSeq();
        long todoSequenceId = 1;

        //when
        todoRepository.updateCheckStatusToCompleted(schedulerSequenceId, todoSequenceId);

        //then
        Todo todo = todoRepository.find(schedulerSequenceId, todoSequenceId)
                .orElseThrow(IllegalArgumentException::new);
        assertThat(todo.getCompleteStatus()).isEqualTo(CompleteStatus.COMPLETED);
    }
    
    @Test
    @DisplayName("Todo 완료 상태를 Uncompleted로 변경하면 업데이트가 되어야 한다.")
    void whenTodoCheckStatusIsUpdatedToUnCompleted_thenTodoCompleteStatusShouldBeUpdated() {
        //given
        int schedulerSequenceId = scheduler.getSchedulerSeq();
        long todoSequenceId = 2;
        
        //when
        todoRepository.updateCheckStatusToUncompleted(schedulerSequenceId, todoSequenceId);
        
        //then
        Todo todo = todoRepository.find(schedulerSequenceId, todoSequenceId)
                .orElseThrow(IllegalArgumentException::new);
        assertThat(todo.getCompleteStatus()).isEqualTo(CompleteStatus.UNCOMPLETED);
    }
}