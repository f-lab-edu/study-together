package dev.flab.studytogether.domain.room.service;

import dev.flab.studytogether.domain.room.entity.Participant;
import dev.flab.studytogether.domain.room.entity.Participants;
import dev.flab.studytogether.domain.room.entity.StudyRoom;
import dev.flab.studytogether.domain.room.exception.RoomEntryException;
import dev.flab.studytogether.domain.room.repository.ParticipantRepository;
import dev.flab.studytogether.domain.room.repository.StudyRoomRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class StudyRoomExitService {

    private final StudyRoomRepository studyRoomRepository;
    private final ParticipantRepository participantRepository;

    public StudyRoom exitRoom(long roomId, int memberSequenceId) {
        StudyRoom studyRoom = findByRoomId(roomId);

        Participant participant = participantRepository
                .findByMemberId(memberSequenceId)
                .orElseThrow(() -> new IllegalArgumentException("방에 존재하지 않는 유저입니다."));

        studyRoom.exitRoom(participant);

        participantRepository.delete(participant);
        studyRoomRepository.update(studyRoom);

        return studyRoom;
    }

    private StudyRoom findByRoomId(long roomId) {
        StudyRoom studyRoom = studyRoomRepository.findByRoomId(roomId)
                .orElseThrow(() -> new RoomEntryException("존재하지 않는 방입니다"));

        return StudyRoom.builder()
                .roomId(studyRoom.getRoomId())
                .roomName(studyRoom.getRoomName())
                .maxParticipants(studyRoom.getMaxParticipants())
                .roomCreateDateTime(studyRoom.getRoomCreateDateTime())
                .participants(new Participants(participantRepository.findByRoomId(roomId)))
                .activateStatus(studyRoom.getActivateStatus())
                .build();
    }
}
