package dev.flab.studytogether.domain.schedule.repository;

import dev.flab.studytogether.domain.schedule.entity.Scheduler;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class SchedulerRepositoryImpl implements SchedulerRepository {

    private final JdbcTemplate jdbcTemplate;

    public SchedulerRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public Optional<Scheduler> findByIdAndDate(int seqId, LocalDate date) {
        try {
            Scheduler scheduler = jdbcTemplate.queryForObject(
                    "select * from scheduler where member_seq_id = ? and date = ?", schedulerRowMapper(), seqId, date);

            return Optional.of(scheduler);
        }
        catch (EmptyResultDataAccessException e){
            return Optional.empty();
        }
    }

    @Override
    public Scheduler save(LocalDate date, int seqId) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("SCHEDULER").usingGeneratedKeyColumns("scheduler_seq");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("date", date);
        parameters.put("member_seq_id", seqId);

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        int schedulerSeq = key.intValue();
        return new Scheduler(schedulerSeq, date, seqId);
    }

    private RowMapper<Scheduler> schedulerRowMapper(){
        return (rs, rowNum) -> new Scheduler(
                rs.getInt("scheduler_seq"),
                rs.getDate("date").toLocalDate(),
                rs.getInt("member_seq_id"));
    }
}
