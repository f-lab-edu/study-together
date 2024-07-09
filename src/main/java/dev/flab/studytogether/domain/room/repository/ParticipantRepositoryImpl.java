package dev.flab.studytogether.domain.room.repository;

import dev.flab.studytogether.domain.room.entity.Participant;
import dev.flab.studytogether.domain.room.entity.ParticipantRole;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class ParticipantRepositoryImpl implements ParticipantRepository {

    private final JdbcTemplate jdbcTemplate;

    public ParticipantRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(Participant participant) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("PARTICIPANT");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("ROOM_ID", participant.getRoomId());
        parameters.put("SEQ_ID", participant.getMemberSequenceId());
        parameters.put("ROLE", participant.getParticipantRole().getRoleName());
        parameters.put("ENTRY_TIME", participant.getRoomEntryTime());

        jdbcInsert.execute(new MapSqlParameterSource(parameters));
    }

    @Override
    public void delete(Participant participant) {
        jdbcTemplate.update("delete from PARTICIPANT " +
                        "where room_id =? and seq_id = ?",
                        participant.getRoomId(), participant.getMemberSequenceId());
    }

    @Override
    public List<Participant> findByRoomId(long roomId) {
        String query = "select * from PARTICIPANT WHERE ROOM_ID =" + roomId;
        return jdbcTemplate.query(query, participantRowMapper());
    }

    @Override
    public Optional<Participant> findByMemberId(int memberSequenceId) {
        try {
            Participant participant =
                    jdbcTemplate.queryForObject("select * from PARTICIPANT WHERE ROOM_ID = ?", participantRowMapper(), memberSequenceId);
            assert participant != null;
            return Optional.of(participant);
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }

    private RowMapper<Participant> participantRowMapper() {
        return (rs, rowNum) -> new Participant(
                    rs.getInt("ROOM_ID"),
                    rs.getInt("SEQ_ID"),
                    ParticipantRole.findByRoleName(rs.getString("ROLE")),
                    rs.getTimestamp("ENTRY_TIME").toLocalDateTime());
    }
}
