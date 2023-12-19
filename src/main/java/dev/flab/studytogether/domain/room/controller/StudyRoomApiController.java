package dev.flab.studytogether.domain.room.controller;

import dev.flab.studytogether.aop.PostMethodLog;
import dev.flab.studytogether.domain.room.dto.RoomCreateRequest;
import dev.flab.studytogether.domain.room.dto.StudyRoomResponse;
import dev.flab.studytogether.domain.room.entity.StudyRoom;
import dev.flab.studytogether.domain.room.service.StudyRoomService;

import dev.flab.studytogether.response.ApiResponse;
import dev.flab.studytogether.utils.SessionUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;

import java.util.List;


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
    public ApiResponse<StudyRoomResponse> createRoom(RoomCreateRequest requestDto, HttpSession httpSession) {
        int memberSeqId = getMemberSequenceId(httpSession);
        StudyRoom studyRoom = studyRoomService.createRoom(requestDto.getRoomName(), requestDto.getTotal(), memberSeqId);

        return ApiResponse.ok(new StudyRoomResponse(studyRoom.getRoomId(), studyRoom.getRoomName(), studyRoom.getMaxParticipants(), studyRoom.getCurrentParticipants(), studyRoom.getManagerSequenceId()));
    }
    @GetMapping("/api/v1/rooms/{roomId}")
    @Operation(summary = "Enter Room", description = "스터디룸 입장")
    public ApiResponse<StudyRoomResponse> enterRoom(@PathVariable int roomId, HttpSession httpSession){
        int memberSeqId = getMemberSequenceId(httpSession);
        StudyRoom studyRoom = studyRoomService.enterRoom(roomId, memberSeqId);

        return ApiResponse.ok(new StudyRoomResponse(studyRoom.getRoomId(), studyRoom.getRoomName(), studyRoom.getMaxParticipants(), studyRoom.getCurrentParticipants(), studyRoom.getManagerSequenceId()));

    }
    @DeleteMapping("/api/v1/rooms/{roomId}")
    @Operation(summary = "Exit Room", description = "스터디룸 퇴장")
    public ApiResponse<StudyRoomResponse> exitRoom(@PathVariable int roomId, HttpSession httpSession){
        int memberSeqId = getMemberSequenceId(httpSession);
        StudyRoom studyRoom = studyRoomService.exitRoom(roomId, memberSeqId);

        return ApiResponse.ok(new StudyRoomResponse(studyRoom.getRoomId(), studyRoom.getRoomName(), studyRoom.getMaxParticipants(), studyRoom.getCurrentParticipants(), studyRoom.getManagerSequenceId()));
    }


    @GetMapping("api/v1/rooms/activated")
    public ApiResponse<List<StudyRoomResponse>> getActivatedStudyRooms() {
        List<StudyRoom> studyRooms = studyRoomService.getActivatedStudyRooms();

        List<StudyRoomResponse> studyRoomResponses = studyRooms.stream()
                .map(StudyRoomResponse::from)
                .toList();

        return ApiResponse.ok(studyRoomResponses);
    }

    @GetMapping("/api/v1/rooms/enter-available")
    public ApiResponse<List<StudyRoomResponse>> getEnterAvailableStudyRooms() {
        List<StudyRoom> studyRooms = studyRoomService.getEnterAvailableStudyRooms();

        List<StudyRoomResponse> studyRoomResponses = studyRooms.stream()
                .map(StudyRoomResponse::from)
                .toList();

        return ApiResponse.ok(studyRoomResponses);

    private int getMemberSequenceId(HttpSession httpSession) {
        return SessionUtil.getLoginMemebrSeqId(httpSession);
    }

    private int getMemberSequenceId(HttpSession httpSession) {
        return SessionUtil.getLoginMemberSequenceId(httpSession);
    }


}
