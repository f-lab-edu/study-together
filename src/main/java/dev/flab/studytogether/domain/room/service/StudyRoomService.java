package dev.flab.studytogether.domain.room.service;

import dev.flab.studytogether.domain.room.entity.*;
import dev.flab.studytogether.domain.room.exception.RoomEntryException;
import dev.flab.studytogether.domain.room.exception.RoomNotFoundException;
import dev.flab.studytogether.domain.room.repository.ParticipantRepository;
import dev.flab.studytogether.domain.room.repository.StudyRoomRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class StudyRoomService {

    private final StudyRoomRepository studyRoomRepository;
    private final ParticipantRepository participantRepository;

    public StudyRoomService(StudyRoomRepository studyRoomRepository, ParticipantRepository participantRepository) {
        this.studyRoomRepository = studyRoomRepository;
        this.participantRepository = participantRepository;
    }

    public StudyRoom createRoom(String roomName, int maxParticipants, int memberSequenceId) {
        StudyRoom studyRoom = studyRoomRepository.save(StudyRoom.builder()
                                                            .roomName(roomName)
                                                            .maxParticipants(maxParticipants)
                                                            .roomCreateDateTime(LocalDateTime.now())
                                                            .activateStatus(ActivateStatus.ACTIVATED)
                                                            .build());

        return enterRoom(studyRoom.getRoomId(), memberSequenceId, ParticipantRole.ROOM_MANAGER);
    }

    public StudyRoom enterRoom(long roomId, int memberSequenceId, ParticipantRole participantRole) {
        StudyRoom studyRoom = findByRoomId(roomId);

        if (studyRoom.isRoomFull()) {
            throw new RoomEntryException("정원이 초과하여 입장 불가합니다.");
        }

        Participant participant =
                new Participant(studyRoom.getRoomId(),
                        memberSequenceId,
                        participantRole,
                        LocalDateTime.now());

        if (studyRoom.isMemberExists(participant)) {
            throw new RoomEntryException("이미 입장 한 방입니다");
        }

        studyRoom.enterRoom(participant);

        participantRepository.save(participant);
        studyRoomRepository.update(studyRoom);

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

    public StudyRoom getRoomInformation(long roomId) {
        return findByRoomId(roomId);
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
