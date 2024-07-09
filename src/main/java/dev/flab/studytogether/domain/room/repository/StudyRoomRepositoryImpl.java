package dev.flab.studytogether.domain.room.repository;

import dev.flab.studytogether.domain.room.entity.ActivateStatus;
import dev.flab.studytogether.domain.room.entity.StudyRoom;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class StudyRoomRepositoryImpl implements StudyRoomRepository {

    private final JdbcTemplate jdbcTemplate;

    public StudyRoomRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public StudyRoom save(StudyRoom studyRoom) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("STUDY_ROOM").usingGeneratedKeyColumns("ROOM_ID");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("ROOM_NAME", studyRoom.getRoomName());
        parameters.put("MAX_PARTICIPANTS", studyRoom.getMaxParticipants());
        parameters.put("CURRENT_PARTICIPANTS", 0);
        parameters.put("CREATE_DATE", studyRoom.getRoomCreateDateTime());
        parameters.put("ACTIVATED", ActivateStatus.ACTIVATED.getStatusValue());
        parameters.put("MANAGER_SEQ_ID", studyRoom.getRoomManager().getMemberSequenceId());

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));

        return StudyRoom.builder()
                .roomId(key.intValue())
                .roomName(studyRoom.getRoomName())
                .maxParticipants(studyRoom.getMaxParticipants())
                .roomCreateDateTime(studyRoom.getRoomCreateDateTime())
                .activateStatus(ActivateStatus.ACTIVATED)
                .build();
    }

    @Override
    public Optional<StudyRoom> findByRoomId(long roomId) {
        try {
            StudyRoom studyRoom = jdbcTemplate.queryForObject("select * from study_room where room_id = ?", studyRoomRowMapper(), roomId);
            assert studyRoom != null;
            return Optional.of(studyRoom);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public void update(StudyRoom studyRoom) {
        String updateQuery = "UPDATE STUDY_ROOM " +
                "SET ROOM_NAME = ?, " +
                "MAX_PARTICIPANTS = ?, " +
                "CURRENT_PARTICIPANTS = ?, " +
                "MANAGER_SEQ_ID = ? " +
                "WHERE ROOM_ID = ?";

        jdbcTemplate.update(updateQuery,
                studyRoom.getRoomName(),
                studyRoom.getMaxParticipants(),
                studyRoom.getCurrentParticipantsCount(),
                studyRoom.getRoomManager().getMemberSequenceId(),
                studyRoom.getRoomId());
    }


    @Override
    public List<StudyRoom> findByActivatedTrue() {
        String query = "SELECT * FROM STUDY_ROOM WHERE ACTIVATED = TRUE";
        return jdbcTemplate.query(query, studyRoomRowMapper());
    }


    private RowMapper<StudyRoom> studyRoomRowMapper(){
        return (rs, rowNum) -> StudyRoom.builder()
                .roomId(rs.getInt("room_id"))
                .roomName(rs.getString("room_name"))
                .maxParticipants(rs.getInt("max_participants"))
                .roomCreateDateTime(rs.getTimestamp("create_date").toLocalDateTime())
                .activateStatus(ActivateStatus.findByStatus(rs.getBoolean("activated")))
                .build();
    }
}
