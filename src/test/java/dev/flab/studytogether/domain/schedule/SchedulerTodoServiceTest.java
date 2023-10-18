package dev.flab.simpleweather.domain.schedule;

import dev.flab.simpleweather.domain.member.Member;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

class SchedulerTodoServiceTest {

    private SchedulerRepository schedulerRepository = Mockito.mock(SchedulerRepository.class);
    private TodoRepository todoRepository = Mockito.mock(TodoRepository.class);
    private SchedulerTodoService schedulerTodoService = new SchedulerTodoService(schedulerRepository, todoRepository);

    @Test
    void 존재하는_날짜_스케줄러가_없으면_새Sequence를_가진_스케줄러생성후_응답객체와_Sequence_일치(){
        String id = "testID";
        int seqId = 1;
        LocalDate date = LocalDate.parse("2023-10-01");
        List<String> todos = new ArrayList<>();

        int newSchedulerSeq = 1;
        given(schedulerRepository.find(seqId, date)).willReturn(Optional.empty());
        given(schedulerRepository.createScheduler(date, seqId,id))
                .willReturn(new Scheduler(newSchedulerSeq,date,seqId,id));

        SchedulerApiResponse schedulerApiResponse = schedulerTodoService.create(seqId, id, date, todos);


        assertEquals(schedulerApiResponse.getSchedulerSeq(), newSchedulerSeq);

    }


    @Test
    void 존재하는_날짜_스케줄러가_있으면_기존스케줄러에_추가후_응답객체와_기존스케줄러_Sequence_일치(){
        String id = "testID";
        int seqId = 1;
        LocalDate date = LocalDate.parse("2023-10-01");
        List<String> todos = new ArrayList<>();
        int schedulerSeq = 1;

        Scheduler scheduler = new Scheduler(schedulerSeq,date,seqId,id);

        given(schedulerRepository.find(seqId, date)).willReturn(Optional.of(scheduler));
        SchedulerApiResponse schedulerApiResponse = schedulerTodoService.create(seqId, id, date, todos);

        assertEquals(schedulerApiResponse.getSchedulerSeq(), schedulerSeq);
    }

}