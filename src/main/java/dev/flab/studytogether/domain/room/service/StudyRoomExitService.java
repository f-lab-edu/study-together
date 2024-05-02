package dev.flab.studytogether.domain.room.service;

import dev.flab.studytogether.domain.room.entity.Participant;
import dev.flab.studytogether.domain.room.entity.StudyRoom;
import dev.flab.studytogether.domain.room.exception.RoomNotFoundException;
import dev.flab.studytogether.domain.room.repository.ParticipantRepository;
import dev.flab.studytogether.domain.room.repository.StudyRoomRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class StudyRoomExitService {

    private final StudyRoomRepository studyRoomRepository;
    private final ParticipantRepository participantRepository;


    public StudyRoom exitRoom(int roomId, int memberSequenceId) {
        StudyRoom studyRoom = studyRoomRepository.findByRoomId(roomId).orElseThrow(() ->
                new RoomNotFoundException("방이 존재하지 않습니다."));

        studyRoom.exitRoom();

        // 방장 권한을 가진 유저일 경우 방장 권한 위임
        if (studyRoom.isRoomManager(memberSequenceId)) {
            transferRoomAdminRole(memberSequenceId, studyRoom);
        }

        participantRepository.delete(studyRoom.getRoomId(), memberSequenceId);
        studyRoomRepository.update(studyRoom.getRoomId(),
                studyRoom.getRoomName(),
                studyRoom.getMaxParticipants(),
                studyRoom.getCurrentParticipants(),
                studyRoom.getManagerSequenceId());

        return studyRoom;
    }


    // 방장 권한을 가졌을 시 권한은 다른 사용자에게 위임 하는 메서드
    private void transferRoomAdminRole(int currentAdminSequenceID, StudyRoom studyRoom) {
        Optional<Participant> nextAdmin =
                findNextAdmin(currentAdminSequenceID, studyRoom.getRoomId());

        nextAdmin.ifPresent(participant -> studyRoom.changeRoomManager(participant.getMemberSequenceId()));
    }

    // 권한 위임할 유저 찾는 메서드
    private Optional<Participant> findNextAdmin(int currentAdminSequenceID, int roomId) {
        List<Participant> participants = participantRepository.findByRoomId(roomId);

        // 입장 시간이 가장 빠른 유저에게 위임
        return participants.stream()
                .filter(participant -> participant.getMemberSequenceId() != currentAdminSequenceID)
                .min(Comparator.comparing(Participant::getRoomEntryTime));
    }
}
