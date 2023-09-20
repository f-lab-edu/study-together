package dev.flab.simpleweather.domain.schedule;

import dev.flab.simpleweather.domain.member.Member;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class SchedulerRespositoryImpl implements SchedulerRepository {

    private final JdbcTemplate jdbcTemplate;

    public SchedulerRespositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Scheduler createScheduler(Scheduler scheduler) {

        List<Scheduler> result = jdbcTemplate.query(
                "select * from scheduler where seq_id = ? and date = ?", schedulerRowMapper(), scheduler.getSeqId(), scheduler.getDate());

        if(result.isEmpty()){
            SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
            jdbcInsert.withTableName("SCHEDULER").usingGeneratedKeyColumns("scheduler_seq");

            Map<String, Object> parameters = new HashMap<>();
            parameters.put("date", scheduler.getDate());
            parameters.put("seq_id", scheduler.getSeqId());
            parameters.put("id", scheduler.getId());


            Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
            int schedulerSeq = key.intValue();
            scheduler.setSchedulerSeq(schedulerSeq);

            return scheduler;
        }
        else {
            scheduler.setSchedulerSeq(result.get(0).getSchedulerSeq());
            return scheduler;
        }




    }

    private RowMapper<Scheduler> schedulerRowMapper(){
        return new RowMapper<Scheduler>() {
            @Override
            public Scheduler mapRow(ResultSet rs, int rowNum) throws SQLException {

                Scheduler scheduler = Scheduler.ofWithSeqID(
                        rs.getInt("scheduler_seq"),
                        rs.getString("date"),
                        rs.getString("seq_id"),
                        rs.getString("id"));

                /*
                member.setSeqID(rs.getInt("seq_id"));
                member.setId(rs.getString("id"));
                member.setPw(rs.getString("pw"));
                member.setNickname(rs.getString("nickname"));


                return member;
*/
                return scheduler;
            }
        };
    }
}
