package dev.flab.simpleweather.domain.schedule;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class TodoRepositoryImpl implements TodoRepository {

    private final JdbcTemplate jdbcTemplate;

    public TodoRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int createTodo(String[] todos,Scheduler scheduler) {

        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);

        //List<String> todos = schedulerForm.getTodos();
        jdbcInsert.withTableName("TODO").usingGeneratedKeyColumns("TODO_SEQ");
        for(String todo : todos){

            Map<String, Object> parameters = new HashMap<>();
            parameters.put("SCHEDULER_SEQ", scheduler.getSchedulerSeq());
            parameters.put("SEQ_ID", scheduler.getSeqId());
            parameters.put("ID", scheduler.getId());
            parameters.put("SCHEDULER_DATE", scheduler.getDate());
            parameters.put("TODO", todo );
            parameters.put("CHECKED", "F");

            Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        }


        /*

        int roomId = key.intValue();
        studyRoom.setRoomId(roomId);

         */
        return scheduler.getSchedulerSeq();
    }
}
