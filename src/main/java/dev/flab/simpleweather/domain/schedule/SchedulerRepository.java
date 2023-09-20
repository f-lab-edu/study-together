package dev.flab.simpleweather.domain.schedule;

import dev.flab.simpleweather.domain.room.StudyRoom;
import org.springframework.stereotype.Repository;

import javax.servlet.http.HttpSession;

@Repository
public interface SchedulerRepository {

    Scheduler createScheduler(Scheduler scheduler);
}
