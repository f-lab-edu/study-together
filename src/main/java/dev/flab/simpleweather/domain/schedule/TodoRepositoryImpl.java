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
    public void createTodo(List<String> todos, Scheduler scheduler) {

        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);

        jdbcInsert.withTableName("TODO").usingGeneratedKeyColumns("TODO_SEQ");

        for(String newTodo : todos){
            Todo todo = Todo.of(scheduler.getSchedulerSeq(), newTodo, Todo.CheckStatus.UNCHECKED);

            Map<String, Object> parameters = new HashMap<>();
            parameters.put("SCHEDULER_SEQ", todo.getSchedulerSeq());
            parameters.put("TODO", newTodo );
            parameters.put("CHECKED", todo.getCheckStatus().getStatus());

            Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        }

    }
}
