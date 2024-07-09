package dev.flab.studytogether.domain.room.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import dev.flab.studytogether.domain.room.entity.*;
import dev.flab.studytogether.fakerepositories.FakeParticipantRepository;
import dev.flab.studytogether.fakerepositories.FakeStudyRoomRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.List;

class StudyRoomExitServiceTest {
    private StudyRoomExitService studyRoomExitService;

    @BeforeEach
    void setup() {
        FakeStudyRoomRepository fakeStudyRoomRepository = new FakeStudyRoomRepository();
        FakeParticipantRepository fakeParticipantRepository = new FakeParticipantRepository();

        this.studyRoomExitService = new StudyRoomExitService(
                fakeStudyRoomRepository,
                fakeParticipantRepository);

        Participant participant1 = new Participant(1, 1, ParticipantRole.ROOM_MANAGER, LocalDateTime.of(2024, 2, 21, 9, 1));
        Participant participant2 = new Participant(1, 2, ParticipantRole.ORDINARY_PARTICIPANT, LocalDateTime.of(2024, 2, 21, 9, 3));
        Participant participant3 = new Participant(1, 3, ParticipantRole.ORDINARY_PARTICIPANT, LocalDateTime.of(2024, 2, 21, 9, 3));

        StudyRoom studyRoom = StudyRoom.builder()
                .roomId(1)
                .roomName("Test Room")
                .maxParticipants(10)
                .roomCreateDateTime(LocalDateTime.of(2024, 1, 31, 9, 1))
                .participants(new Participants(List.of(participant1, participant2, participant3)))
                .activateStatus(ActivateStatus.ACTIVATED)
                .build();

        fakeStudyRoomRepository.save(studyRoom);
        fakeParticipantRepository.save(participant1);
        fakeParticipantRepository.save(participant2);
        fakeParticipantRepository.save(participant3);
    }
    @Test
    @DisplayName("방장이 퇴장할 경우 방장 권한이 방장 다음 입장 시간이 가장 빠른 유저에게 위임된다")
    void roomManagerExitTest() {
        //given
        int roomId = 1;
        int roomManagerMemberSequenceId = 1;

        //when
        StudyRoom studyRoom = studyRoomExitService.exitRoom(roomId, roomManagerMemberSequenceId);

        //then
        assertEquals(2, studyRoom.getRoomManager().getMemberSequenceId());
    }

    @Test
    @DisplayName("유저가 방에서 퇴장할 경우 현재 참여자 수가 감소한다.")
    void roomExitTest() {
        //given
        int roomId = 1;
        int memberSequenceId = 2;

        // when
        StudyRoom studyRoom = studyRoomExitService.exitRoom(roomId, memberSequenceId);

        // then
        assertEquals(2, studyRoom.getCurrentParticipantsCount());
    }
}

