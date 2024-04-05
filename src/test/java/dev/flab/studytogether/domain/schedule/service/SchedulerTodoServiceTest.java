package dev.flab.studytogether.domain.schedule.service;

import dev.flab.studytogether.domain.schedule.dto.SchedulerTodoDto;
import dev.flab.studytogether.domain.schedule.entity.Scheduler;
import dev.flab.studytogether.domain.schedule.entity.Todo;
import dev.flab.studytogether.fakerepositories.FakeSchedulerRepository;
import dev.flab.studytogether.fakerepositories.FakeTodoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


class SchedulerTodoServiceTest {

    private final FakeSchedulerRepository fakeSchedulerRepository = new FakeSchedulerRepository();
    private final FakeTodoRepository fakeTodoRepository = new FakeTodoRepository();
    private final SchedulerTodoService schedulerTodoService =
            new SchedulerTodoService(fakeSchedulerRepository, fakeTodoRepository);

    @BeforeEach
    void setup() {
        LocalDate schedulerDate = LocalDate.of(2024, 2, 1);
        int memberSequenceId = 1;
        String todoContent = "투두 테스트";

        Scheduler scheduler = fakeSchedulerRepository.save(schedulerDate, memberSequenceId);
        fakeTodoRepository.save(todoContent, scheduler);
    }
    
    @Test
    @DisplayName("기존 생성된 스케줄러 날짜와 동일한 날짜에 스케줄러-투두 생성시 Scheduler Sequence ID가 동일하다.")
    void createSchedulerTodoOnSameDateAsExistingSchedulerSequenceIdRemainsSame() {
        //given
        SchedulerTodoDto schedulerTodoDto = SchedulerTodoDto.builder()
                .memberSequenceId(1)
                .todoContent("투두 테스트")
                .schedulerDate(LocalDate.of(2024, 2, 1))
                .build();


        //when
        SchedulerTodoDto resultSchedulerTodoDto =
                schedulerTodoService.create(schedulerTodoDto);
        
        //then
        int existingSchedulerSequenceId = 1;
        assertEquals(existingSchedulerSequenceId, resultSchedulerTodoDto.getSchedulerSequenceID());
    }

    @Test
    @DisplayName("기존 생성된 날짜와 다른 날짜에 스케줄러-투두 생성시 Scheduler Sequence ID가 증가 한다")
    void createSchedulerTodoOnDifferentDateAsExistingSchedulerSequenceIdIncreases() {
        //given
        SchedulerTodoDto schedulerTodoDto = SchedulerTodoDto.builder()
                .memberSequenceId(1)
                .todoContent("투두 테스트")
                .schedulerDate(LocalDate.of(2024, 2, 2))
                .build();

        //when
        int expectedSchedulerSequenceId =
                fakeSchedulerRepository.getMaxSchedulerSequenceId() + 1;

        SchedulerTodoDto resultSchedulerTodoDto =
                schedulerTodoService.create(schedulerTodoDto);

        //then
        assertEquals(expectedSchedulerSequenceId, resultSchedulerTodoDto.getSchedulerSequenceID());
    }

    @Test
    @DisplayName("Scheduler-Todo 생성 시 Todo Sequence ID가 증가 한다")
    void createSchedulerTodoThenTodoSequenceIdIncreases() {
        //given
        SchedulerTodoDto schedulerTodoDto = SchedulerTodoDto.builder()
                .memberSequenceId(1)
                .todoContent("투두 테스트")
                .schedulerDate(LocalDate.of(2024, 2, 1))
                .build();

        //when
        long expectedTodoSequenceId =
                fakeTodoRepository.getMaxTodoSequenceId() + 1;

        SchedulerTodoDto resultSchedulerTodoDto =
                schedulerTodoService.create(schedulerTodoDto);

        //then
        assertEquals(expectedTodoSequenceId, resultSchedulerTodoDto.getTodoSequenceID());
    }

    @Test
    @DisplayName("투두를 삭제하면 repository에서 해당 투두가 삭제 된다.")
    void testDeleteTodo() {
        //given
        int schedulerSequenceId = 1;
        long todoSequenceId = 1L;
        SchedulerTodoDto schedulerTodoDto = SchedulerTodoDto.builder()
                .memberSequenceId(1)
                .schedulerSequenceID(schedulerSequenceId)
                .todoSequenceID(todoSequenceId)
                .build();

        //when
        schedulerTodoService.deleteTodo(schedulerTodoDto);

        //then
        assertTrue(fakeTodoRepository.find(schedulerSequenceId, todoSequenceId).isEmpty());
    }
    
    @Test
    @DisplayName("투두 Content update시 해당 투두 Content가 변경 된다")
    void testUpdateTodoContent() {
        //given
        int schedulerSequenceId = 1;
        long todoSequenceId = 1L;
        String updateTodoContent = "Changed Content";
        SchedulerTodoDto schedulerTodoDto = SchedulerTodoDto.builder()
                .schedulerSequenceID(schedulerSequenceId)
                .todoSequenceID(todoSequenceId)
                .todoContent(updateTodoContent)
                .build();
        
        //when
        schedulerTodoService.updateTodoContent(schedulerTodoDto);
        
        //then
        Todo todo = fakeTodoRepository.find(schedulerSequenceId, todoSequenceId).get();
        assertEquals(updateTodoContent, todo.getTodoContent());
    }
    
    @Test
    @DisplayName("완료 상태로 업데이트시 해당 투두의 상태가 변경 된다.")
    void testUpdateStatus() {
        //given
        int schedulerSequenceId = 1;
        long todoSequenceId = 1L;
        SchedulerTodoDto schedulerTodoDto = SchedulerTodoDto.builder()
                .schedulerSequenceID(schedulerSequenceId)
                .todoSequenceID(todoSequenceId)
                .build();
        
        //when
        schedulerTodoService.updateToCompleted(schedulerTodoDto);
        
        //then
        Todo todo = fakeTodoRepository.find(schedulerSequenceId, todoSequenceId).get();
        assertEquals(Todo.CompleteStatus.COMPLETED, todo.getCompleteStatus());
    }


}