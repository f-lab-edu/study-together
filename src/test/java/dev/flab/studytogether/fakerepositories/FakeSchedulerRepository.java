package dev.flab.studytogether.fakerepositories;

import dev.flab.studytogether.domain.schedule.entity.Scheduler;
import dev.flab.studytogether.domain.schedule.repository.SchedulerRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Optional;

public class FakeSchedulerRepository implements SchedulerRepository {
    private final Collection<Scheduler> fakeSchedulers = new ArrayList<>();

    @Override
    public Optional<Scheduler> findByMemberIdAndDate(int memberSequenceId, LocalDate date) {
        return fakeSchedulers.stream()
                .filter(x -> x.getSchedulerSeq() == memberSequenceId && x.getDate().equals(date))
                .findFirst();
    }

    @Override
    public Scheduler save(LocalDate date, int seqId) {
        Scheduler newScheduler = new Scheduler(
                getMaxSchedulerSequenceId() + 1,
                date,
                seqId);

        fakeSchedulers.add(newScheduler);
        return newScheduler;
    }

    public int getMaxSchedulerSequenceId() {
        Optional<Scheduler> schedulerWithMaxSequenceId = fakeSchedulers.stream()
                .max(Comparator.comparing(Scheduler::getSchedulerSeq));

        return schedulerWithMaxSequenceId
                .map(Scheduler::getSchedulerSeq)
                .orElse(0);
    }
}
