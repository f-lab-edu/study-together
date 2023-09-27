package dev.flab.simpleweather.domain.schedule;

import dev.flab.simpleweather.domain.room.StudyRoom;
import org.springframework.stereotype.Repository;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface SchedulerRepository {



    Optional<Scheduler> find(int seqId, LocalDate date);



    Scheduler createScheduler(LocalDate date, int seqId, String id);
}
