package dev.flab.simpleweather.domain.room;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Repository
public class StudyRoomRepositoryImpl implements StudyRoomRepository {

    private final JdbcTemplate jdbcTemplate;

    public StudyRoomRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public int createRoom(StudyRoom studyRoom, HttpSession httpSession) {

        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("STUDY_ROOM").usingGeneratedKeyColumns("ROOM_ID");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("ROOM_NAME", studyRoom.getRoomName());
        parameters.put("TOTAL", studyRoom.getTotal());
        parameters.put("CREATE_DATE", studyRoom.getCreateDate());
        parameters.put("PARTICIPANTS_NUM", 1);
        parameters.put("STATUS", "T");
        parameters.put("MANAGER_SEQ_ID", httpSession.getAttribute("seq_id"));

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        int roomId = key.intValue();
        studyRoom.setRoomId(roomId);


        return roomId;
    }
}
