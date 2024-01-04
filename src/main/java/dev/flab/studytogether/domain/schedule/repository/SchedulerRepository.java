package dev.flab.studytogether.domain.schedule.repository;

import dev.flab.studytogether.domain.schedule.entity.Scheduler;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface SchedulerRepository {



    Optional<Scheduler> find(int seqId, LocalDate date);

    Scheduler createScheduler(LocalDate date, int seqId);
}
