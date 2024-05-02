package dev.flab.studytogether.domain.schedule.repository;

import static org.assertj.core.api.Assertions.*;

import dev.flab.studytogether.domain.schedule.entity.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.junit.jupiter.api.*;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.util.Optional;


@JdbcTest
@Sql({"classpath:schema.sql", "classpath:test-data.sql"})
class SchedulerRepositoryTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private SchedulerRepository schedulerRepository;

    @BeforeEach
    void setup() {
        this.schedulerRepository = new SchedulerRepositoryImpl(jdbcTemplate);
    }

    @Test
    @DisplayName("데이터베이스에 존재하는 Member ID와 날짜로 스케줄러 찾으면 스케줄러를 반환한다.")
    void findByMemberIdAndDate_WhenSchedulerExists_ThenReturnsOptionalOfExistingScheduler() {
        //given
        int memberSequenceId = 1;
        LocalDate schedulerDate = LocalDate.of(2024, 5, 1);

        //when
        Optional<Scheduler> scheduler =
                schedulerRepository.findByMemberIdAndDate(memberSequenceId, schedulerDate);

        //then
        assertThat(scheduler).isPresent();
    }

    @Test
    @DisplayName("데이터베이스에 존재하지 않는 Member ID와 날짜로 스케줄러 찾으면 Optional.empty()를 반환한다.")
    void findByMemberIdAndDate_WhenSchedulerNotExists_ThenReturnsOptionEmpty() {
        //given
        int memberSequenceId = 302;
        LocalDate schedulerDate = LocalDate.of(2024, 5, 1);

        //when
        Optional<Scheduler> scheduler =
                schedulerRepository.findByMemberIdAndDate(memberSequenceId, schedulerDate);

        //then
        assertThat(scheduler).isEmpty();
    }

    @Test
    @DisplayName("")
    void givenDateAndMemberId_whenSchedulerIsSaved_ThenReturnsSavedSchedulerWithSequenceId() {
        //given
        LocalDate schedulerDate = LocalDate.of(2024, 5, 7);
        int memberSequenceId = 1;

        //when
        Scheduler scheduler = schedulerRepository.save(schedulerDate, memberSequenceId);

        //then
        assertThat(scheduler.getSchedulerSeq()).isPositive();
    }

}