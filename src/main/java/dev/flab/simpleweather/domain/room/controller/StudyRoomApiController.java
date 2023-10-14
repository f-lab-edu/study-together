package dev.flab.simpleweather.domain.room.controller;


import dev.flab.simpleweather.aop.PostMethodLog;
import dev.flab.simpleweather.domain.room.entity.StudyRoom;
import dev.flab.simpleweather.domain.room.StudyRoomApiResponse;
import dev.flab.simpleweather.domain.room.service.StudyRoomService;
import dev.flab.simpleweather.utils.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;

@RestController
public class StudyRoomApiController {
    private final StudyRoomService studyRoomService;

    @Autowired
    public StudyRoomApiController(StudyRoomService studyRoomService) {
        this.studyRoomService = studyRoomService;
    }

    @PostMapping("/api/v1/rooms")
    @PostMethodLog
    public StudyRoomApiResponse createRoom(RoomCreateRequestDto requestDto, HttpSession httpSession) throws SQLException {
        int memberSeqId = SessionUtil.getLoginMemebrSeqId(httpSession);
        StudyRoom studyRoom = studyRoomService.createRoom(requestDto.getRoomName(), requestDto.getTotal(), memberSeqId);

        return StudyRoomApiResponse.from(studyRoom);

    }
    @GetMapping("/rooms/{roomId}")
    public StudyRoomApiResponse enterRoom(@PathVariable int roomId, HttpSession httpSession) throws SQLException {
        int memberSeqId = SessionUtil.getLoginMemebrSeqId(httpSession);
        StudyRoom studyRoom = studyRoomService.enterRoom(roomId, memberSeqId);

        return StudyRoomApiResponse.from(studyRoom);
    }
    @DeleteMapping("/rooms/{roomId}")
    public StudyRoomApiResponse exitRoom(@PathVariable int roomId, HttpSession httpSession){
        int memberSeqId = SessionUtil.getLoginMemebrSeqId(httpSession);
        StudyRoom studyRoom = studyRoomService.exitRoom(roomId, memberSeqId);

        return StudyRoomApiResponse.from(studyRoom);
    }


    /*
    =========== Dto =============
    */
    public class RoomCreateRequestDto{
        private String roomName;
        private int total;

        public RoomCreateRequestDto(String roomName, int total) {
            this.roomName = roomName;
            this.total = total;
        }

        public String getRoomName() {
            return roomName;
        }

        public int getTotal() {
            return total;
        }
    }

}
