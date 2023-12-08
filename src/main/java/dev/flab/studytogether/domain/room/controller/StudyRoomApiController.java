package dev.flab.studytogether.domain.room.controller;

import dev.flab.studytogether.aop.PostMethodLog;
import dev.flab.studytogether.domain.room.dto.RoomCreateRequest;
import dev.flab.studytogether.domain.room.dto.StudyRoomResponse;
import dev.flab.studytogether.domain.room.entity.StudyRoom;
import dev.flab.studytogether.domain.room.service.StudyRoomService;
import dev.flab.studytogether.response.SingleApiResponse;
import dev.flab.studytogether.utils.SessionUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;

@RestController
@Tag(name = "Study Rooom", description = "스터디룸 API")
public class StudyRoomApiController {

    private final StudyRoomService studyRoomService;

    public StudyRoomApiController(StudyRoomService studyRoomService) {
        this.studyRoomService = studyRoomService;
    }

    @PostMapping("/api/v1/rooms")
    @PostMethodLog
    @Operation(summary = "Create room", description = "스터디룸 생성")
    public SingleApiResponse<StudyRoomResponse> createRoom(RoomCreateRequest requestDto, HttpSession httpSession) {
        int memberSeqId = getMemberSequenceId(httpSession);
        StudyRoom studyRoom = studyRoomService.createRoom(requestDto.getRoomName(), requestDto.getTotal(), memberSeqId);

        return SingleApiResponse.ok(new StudyRoomResponse(studyRoom.getRoomId(), studyRoom.getRoomName(), studyRoom.getMaxParticipants(), studyRoom.getCurrentParticipants(), studyRoom.getManagerSequenceId()));
    }
    @GetMapping("/api/v1/rooms/{roomId}")
    @Operation(summary = "Enter Room", description = "스터디룸 입장")
    public SingleApiResponse<StudyRoomResponse> enterRoom(@PathVariable int roomId, HttpSession httpSession){
        int memberSeqId = getMemberSequenceId(httpSession);
        StudyRoom studyRoom = studyRoomService.enterRoom(roomId, memberSeqId);

        return SingleApiResponse.ok(new StudyRoomResponse(studyRoom.getRoomId(), studyRoom.getRoomName(), studyRoom.getMaxParticipants(), studyRoom.getCurrentParticipants(), studyRoom.getManagerSequenceId()));

    }
    @DeleteMapping("/api/v1/rooms/{roomId}")
    @Operation(summary = "Exit Room", description = "스터디룸 퇴장")
    public SingleApiResponse<StudyRoomResponse> exitRoom(@PathVariable int roomId, HttpSession httpSession){
        int memberSeqId = getMemberSequenceId(httpSession);
        StudyRoom studyRoom = studyRoomService.exitRoom(roomId, memberSeqId);

        return SingleApiResponse.ok(new StudyRoomResponse(studyRoom.getRoomId(), studyRoom.getRoomName(), studyRoom.getMaxParticipants(), studyRoom.getCurrentParticipants(), studyRoom.getManagerSequenceId()));
    }

    private int getMemberSequenceId(HttpSession httpSession) {
        return SessionUtil.getLoginMemberSequenceId(httpSession);
    }

}
