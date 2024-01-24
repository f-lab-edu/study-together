package dev.flab.studytogether.domain.room.repository;

import dev.flab.studytogether.domain.room.entity.Participant;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ParticipantRepositoryImpl implements ParticipantRepository {

    private final JdbcTemplate jdbcTemplate;

    public ParticipantRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(int roomId, int seqId, LocalDateTime now) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("PARTICIPANT");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("ROOM_ID", roomId);
        parameters.put("SEQ_ID", seqId);
        parameters.put("ENTRY_TIME", now);

        jdbcInsert.execute(new MapSqlParameterSource(parameters));
    }

    @Override
    public void delete(int roomId, int seqId) {
        jdbcTemplate.update("delete from PARTICIPANT where room_id =? and seq_id = ?", roomId, seqId);
    }

    @Override
    public int countTotalParticipantsNum(int roomId) throws SQLException{
        String query = "select count(*) from PARTICIPANT where room_id =" + roomId;
        return jdbcTemplate.queryForObject(query, Integer.class);
    }

    @Override
    public boolean isMemberExists(int roomId, int memberSequenceId) {
        return jdbcTemplate.queryForObject(
                "SELECT EXISTS (SELECT 1 FROM PARTICIPANT WHERE ROOM_ID =? AND SEQ_ID =?)",
                Boolean.class,
                roomId,
                memberSequenceId);

    }


}
