package dev.flab.studytogether.domain.room.controller;


import dev.flab.studytogether.aop.PostMethodLog;
import dev.flab.studytogether.domain.room.entity.StudyRoom;
import dev.flab.studytogether.domain.room.StudyRoomApiResponse;
import dev.flab.studytogether.domain.room.service.StudyRoomService;
import dev.flab.studytogether.utils.SessionUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;

@RestController
@Tag(name = "Study Rooom", description = "스터디룸 API")
public class StudyRoomApiController {
    private final StudyRoomService studyRoomService;

    @Autowired
    public StudyRoomApiController(StudyRoomService studyRoomService) {
        this.studyRoomService = studyRoomService;
    }

    @PostMapping("/api/v1/rooms")
    @PostMethodLog
    @Operation(summary = "Create room", description = "스터디룸 생성")
    public StudyRoomApiResponse createRoom(RoomCreateRequestDto requestDto, HttpSession httpSession) throws SQLException {
        int memberSeqId = SessionUtil.getLoginMemebrSeqId(httpSession);
        StudyRoom studyRoom = studyRoomService.createRoom(requestDto.getRoomName(), requestDto.getTotal(), memberSeqId);

        return StudyRoomApiResponse.from(studyRoom);

    }
    @GetMapping("/rooms/{roomId}")
    @Operation(summary = "Enter Room", description = "스터디룸 입장")
    public StudyRoomApiResponse enterRoom(@PathVariable int roomId, HttpSession httpSession) throws SQLException {
        int memberSeqId = SessionUtil.getLoginMemebrSeqId(httpSession);
        StudyRoom studyRoom = studyRoomService.enterRoom(roomId, memberSeqId);

        return StudyRoomApiResponse.from(studyRoom);
    }
    @DeleteMapping("/rooms/{roomId}")
    @Operation(summary = "Exit Room", description = "스터디룸 퇴장")
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
