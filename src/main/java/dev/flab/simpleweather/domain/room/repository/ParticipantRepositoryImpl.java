package dev.flab.simpleweather.domain.room.repository;

import dev.flab.simpleweather.domain.room.repository.ParticipantRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Repository
public class ParticipantRepositoryImpl implements ParticipantRepository {

    private final JdbcTemplate jdbcTemplate;

    public ParticipantRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(int roomId, int seqId) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("PARTICIPANT");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("ROOM_ID", roomId);
        parameters.put("SEQ_ID", seqId);

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


}
