package dev.flab.studytogether.domain.room.controller;

import dev.flab.studytogether.domain.room.dto.AuthDelegateDTO;
import dev.flab.studytogether.domain.room.dto.ParticipantResponse;
import dev.flab.studytogether.domain.room.dto.StudyRoomMessage;
import dev.flab.studytogether.domain.room.dto.StudyRoomResponse;
import dev.flab.studytogether.domain.room.entity.StudyRoom;
import dev.flab.studytogether.domain.room.service.StudyRoomExitService;
import dev.flab.studytogether.domain.room.service.StudyRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import java.util.*;

@Controller
@RequiredArgsConstructor
public class StompStudyRoomController {

    private final SimpMessagingTemplate messagingTemplate;
    private final StudyRoomExitService studyRoomExitService;
    private final StudyRoomService studyRoomService;
    private List<ParticipantResponse> participants = new ArrayList<>();

    // 입장 메세지
    @MessageMapping("/room/enter")
    public void greeting(StudyRoomMessage message) {
        message.setMessage(message.getMemberID() + "님이 입장하셨습니다.");
        participants.add(new ParticipantResponse(message.getMemberID(), message.getMemberSequenceID()));
        messagingTemplate.convertAndSend("/subscribe/message/room/" + message.getStudyRoomID(), message);

        sendRoomInfo(message.getStudyRoomID());
        updateParticipants(message.getStudyRoomID());
    }

    // 퇴장 메세지
    @MessageMapping("/room/exit")
    public void exit(StudyRoomMessage message) {
        message.setMessage(message.getMemberID() + "님이 퇴장하셨습니다.");
        participants.removeIf(participant ->
                Objects.equals(participant.getMemberID(), message.getMemberID()));

        messagingTemplate.convertAndSend("/subscribe/message/room/" + message.getStudyRoomID(), message);

        sendRoomInfo(message.getStudyRoomID());
        updateParticipants(message.getStudyRoomID());
    }

    // 방장 권한 위임
    @MessageMapping("/room/host-authorization/delegate")
    public void hostAuthorizationDelegate(AuthDelegateDTO authDelegateDTO) {
        studyRoomExitService.exitRoom(authDelegateDTO.getRoomID(), authDelegateDTO.getDelegatorSequenceID());

        sendRoomInfo(String.valueOf(authDelegateDTO.getRoomID()));
        updateParticipants(String.valueOf(authDelegateDTO.getRoomID()));
    }

    // 스터디룸 정보 클라이언트로 전송
    @MessageMapping("/room/info")
    private void sendRoomInfo(String roomId) {
        StudyRoom studyRoom = studyRoomService.getRoomInformation(Integer.parseInt(roomId));
        StudyRoomResponse studyRoomResponse = StudyRoomResponse.from(studyRoom);
        messagingTemplate.convertAndSend("/subscribe/info/room/" + roomId, studyRoomResponse);
    }

    // 스터디룸 참여자 목록 업데이트시 클라이언트로 전송
    private void updateParticipants(String roomId) {
        messagingTemplate.convertAndSend("/subscribe/participants/room/" + roomId, participants);
    }
}
