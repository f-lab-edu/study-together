package dev.flab.simpleweather.domain.room.repository;

import dev.flab.simpleweather.domain.room.entity.StudyRoom;
import dev.flab.simpleweather.domain.room.repository.StudyRoomRepository;
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
public class StudyRoomRepositoryImpl implements StudyRoomRepository {

    private final JdbcTemplate jdbcTemplate;

    public StudyRoomRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public StudyRoom save(String roomName, int total, int memberSeqId) {
        LocalDate createDate = LocalDate.now();

        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("STUDY_ROOM").usingGeneratedKeyColumns("ROOM_ID");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("ROOM_NAME", roomName);
        parameters.put("TOTAL", total);
        parameters.put("CREATE_DATE", createDate);
        parameters.put("ACTIVATED", StudyRoom.ActivateStatus.ACTIVATED.getStatusValue());
        parameters.put("MANAGER_SEQ_ID", memberSeqId);

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));

        return new StudyRoom.Builder()
                .roomID(key.intValue())
                .roomName(roomName)
                .total(total)
                .createDate(createDate)
                .activateStatus(StudyRoom.ActivateStatus.ACTIVATED)
                .managerSeqId(memberSeqId)
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
    public int findTotalByRoomId(int roomId) {
        return jdbcTemplate.queryForObject("select total from STUDY_ROOM where room_id = ?", Integer.class ,roomId);
    }


    private RowMapper<StudyRoom> studyRoomRowMapper(){
        return (rs, rowNum) -> new StudyRoom.Builder()
                .roomID(rs.getInt("room_id"))
                .roomName(rs.getString("room_name"))
                .total(rs.getInt("total"))
                .createDate(rs.getDate("create_date").toLocalDate())
                .activateStatus(StudyRoom.ActivateStatus.findByStatus(rs.getBoolean("activated")))
                .managerSeqId(rs.getInt("manager_seq_id"))
                .build();

    }



}
