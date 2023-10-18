package dev.flab.studytogether.domain.schedule.repository;

import dev.flab.studytogether.domain.schedule.entity.Scheduler;
import dev.flab.studytogether.domain.schedule.entity.Todo;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class TodoRepositoryImpl implements TodoRepository {

    private final JdbcTemplate jdbcTemplate;

    public TodoRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Todo save(String todoContent, Scheduler scheduler) {

        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);

        jdbcInsert.withTableName("TODO").usingGeneratedKeyColumns("TODO_ID");

        Todo todo = new Todo(scheduler.getSchedulerSeq(), todoContent, Todo.CompleteStatus.UNCOMPLETED);

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("SCHEDULER_SEQ", todo.getSchedulerSeq());
        parameters.put("CONTENT", todo.getTodoContent());
        parameters.put("COMPLETED", todo.getCompleteStatus().getStatus() );

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));

        return Todo.ofWithTodoID(key.longValue(), todo.getSchedulerSeq(), todo.getTodoContent(), todo.getCompleteStatus());
        }

    @Override
    public Optional<Todo> find(int schedulerSeq, long todoID) {
        try{
            Todo todo = jdbcTemplate.queryForObject("select * from TODO where todo_id =? and scheduler_seq = ?", todoRowMapper(),todoID, schedulerSeq);
            return Optional.of(todo);
        } catch(EmptyResultDataAccessException e){
            return Optional.empty();
        }

    }

    @Override
    public void updateContent(String todoContent, int schdeulrSeq, long todoID) {
        jdbcTemplate.update("update TODO set content = ? where todo_id = ? and scheduler_seq = ?",todoContent, todoID, schdeulrSeq);
    }

    @Override
    public void delete(int schedulerSeq, long todoID) {
        jdbcTemplate.update("delete TODO where todo_id =? and scheduler_seq =?",todoID, schedulerSeq);
    }

    @Override
    public void updateCheckStatusToCompleted(int schedulerSeq, long todoID) {
        jdbcTemplate.update("update TODO set COMPLETED = TRUE where todo_id = ? and scheduler_seq = ?", todoID, schedulerSeq);
    }

    @Override
    public void updateCheckStatusToUncompleted(int schedulerSeq, long todoID) {
        jdbcTemplate.update("update TODO set COMPLETED = FALSE where todo_id = ? and scheduler_seq = ?", todoID, schedulerSeq);
    }

    private RowMapper<Todo> todoRowMapper(){
        return (rs, rowNum) -> new Todo(
                rs.getLong("todo_id"),
                rs.getInt("scheduler_seq"),
                rs.getString("content"),
                Todo.CompleteStatus.findByStatus(rs.getBoolean("completed")));

    }
}
