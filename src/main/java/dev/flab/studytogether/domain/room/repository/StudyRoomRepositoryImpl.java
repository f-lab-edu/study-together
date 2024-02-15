package dev.flab.studytogether.domain.room.repository;

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
    public StudyRoom save(String roomName, int maxParticipants, int memberSeqId) {
        LocalDate createDate = LocalDate.now();

        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("STUDY_ROOM").usingGeneratedKeyColumns("ROOM_ID");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("ROOM_NAME", roomName);
        parameters.put("MAX_PARTICIPANTS", maxParticipants);
        parameters.put("CREATE_DATE", createDate);
        parameters.put("ACTIVATED", StudyRoom.ActivateStatus.ACTIVATED.getStatusValue());
        parameters.put("MANAGER_SEQ_ID", memberSeqId);

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));

        return StudyRoom.builder()
                .roomId(key.intValue())
                .roomName(roomName)
                .maxParticipants(maxParticipants)
                .createDate(createDate)
                .activateStatus(StudyRoom.ActivateStatus.ACTIVATED)
                .managerSequenceId(memberSeqId)
                .build();

    }

    @Override
    public Optional<StudyRoom> findByRoomId(int roomId) {
        try {
            StudyRoom studyRoom = jdbcTemplate.queryForObject("select * from study_room where room_id = ?", studyRoomRowMapper(), roomId);
            assert studyRoom != null;
            return Optional.of(studyRoom);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public void update(int roomId, String roomName, int maxParticipants, int currentParticipants, int managerSequenceId) {
        String updateQuery = "UPDATE STUDY_ROOM SET ROOM_NAME = ?, MAX_PARTICIPANTS = ?, CURRENT_PARTICIPANTS = ?, MANAGER_SEQ_ID = ? WHERE ROOM_ID = ?";

        jdbcTemplate.update(updateQuery, roomName, maxParticipants, currentParticipants, managerSequenceId, roomId);
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
                .currentParticipants(rs.getInt("current_participants"))
                .createDate(rs.getDate("create_date").toLocalDate())
                .activateStatus(StudyRoom.ActivateStatus.findByStatus(rs.getBoolean("activated")))
                .managerSequenceId(rs.getInt("manager_seq_id"))
                .build();

    }



}
