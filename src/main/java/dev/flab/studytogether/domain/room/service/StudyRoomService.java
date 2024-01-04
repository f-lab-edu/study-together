package dev.flab.studytogether.domain.room.service;

import dev.flab.studytogether.domain.member.service.AuthService;
import dev.flab.studytogether.domain.room.entity.StudyRoom;
import dev.flab.studytogether.domain.room.exception.RoomEntryException;
import dev.flab.studytogether.domain.room.repository.ParticipantRepository;
import dev.flab.studytogether.domain.room.repository.StudyRoomRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class StudyRoomService {

    private final StudyRoomRepository studyRoomRepository;
    private final ParticipantRepository participantRepository;
    private final AuthService authService;

    public StudyRoomService(StudyRoomRepository studyRoomRepository, ParticipantRepository participantRepository, AuthService authService) {
        this.studyRoomRepository = studyRoomRepository;
        this.participantRepository = participantRepository;
        this.authService = authService;
    }

    public StudyRoom createRoom(String roomName, int total, int memberSequenceId) {
        StudyRoom studyRoom = studyRoomRepository.save(roomName, total, memberSequenceId);
        participantRepository.save(studyRoom.getRoomId(), memberSequenceId);
        authService.grantRoomAdminRole();

        return enterRoom(studyRoom.getRoomId(), memberSequenceId);
    }

    public StudyRoom enterRoom(int roomId, int memberSequenceId) {
        StudyRoom studyRoom = studyRoomRepository.findByRoomId(roomId).orElseThrow(() ->
                new RoomEntryException("존재하지 않는 방입니다"));

        if (studyRoom.isRoomFull()) {
            throw new RoomEntryException("정원이 초과하여 입장 불가합니다");
        }

        if (participantRepository.isMemberExists(roomId, memberSequenceId)) {
            throw new RoomEntryException("이미 입장 한 방입니다");
        }

        studyRoom.enterRoom();

        participantRepository.save(roomId, memberSequenceId);
        studyRoomRepository.update(roomId,
                studyRoom.getRoomName(),
                studyRoom.getMaxParticipants(),
                studyRoom.getCurrentParticipants(),
                studyRoom.getManagerSequenceId()
        );

        return studyRoom;
    }

    public StudyRoom exitRoom(int roomId, int memberSeqId){
        StudyRoom studyRoom = studyRoomRepository.findByRoomId(roomId).orElseThrow();
        participantRepository.delete(studyRoom.getRoomId(), memberSeqId);
        return studyRoom;
    }

    public List<StudyRoom> getActivatedStudyRooms() {
        return studyRoomRepository.findByActivatedTrue();
    }

    public List<StudyRoom> getEnterAvailableStudyRooms() {
        List<StudyRoom> studyRooms = studyRoomRepository.findByActivatedTrue();

        return studyRooms.stream()
                .filter(studyRoom -> !studyRoom.isRoomFull())
                .collect(Collectors.toList());
    }


}
