package dev.flab.studytogether.domain.room.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import dev.flab.studytogether.domain.room.entity.Participant;
import dev.flab.studytogether.domain.room.entity.StudyRoom;
import dev.flab.studytogether.domain.room.repository.ParticipantRepository;
import dev.flab.studytogether.domain.room.repository.StudyRoomRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

class StudyRoomExitServiceTest {
    @Test
    @DisplayName("방장이 퇴장할 경우 방장 권한이 방장 다음 입장 시간이 가장 빠른 유저에게 위임된다")
    void roomManagerExitTest() {
        //given
        StudyRoomExitService studyRoomExitService =
                new StudyRoomExitService(new StubStudyRoomRepository(), new StubParticipantRepository());

        //when
        StudyRoom studyRoom = studyRoomExitService.exitRoom(1, 1);

        //then
        assertEquals(2, studyRoom.getManagerSequenceId());
    }

    @Test
    @DisplayName("유저가 방에서 퇴장할 경우 현재 참여자 수가 감소한다.")
    void roomExitTest() {
        // given
        StudyRoomExitService studyRoomExitService =
                new StudyRoomExitService(new StubStudyRoomRepository(), new StubParticipantRepository());

        // when
        StudyRoom studyRoom = studyRoomExitService.exitRoom(1, 2);

        // then
        assertEquals(2, studyRoom.getCurrentParticipants());
    }

    static class StubStudyRoomRepository implements StudyRoomRepository {

        @Override
        public StudyRoom save(String roomName, int total, int memberSeqId) {
            return null;
        }

        @Override
        public Optional<StudyRoom> findByRoomId(int roomId) {
            return Optional.ofNullable(StudyRoom.builder()
                    .roomId(1)
                    .roomName("Room 1")
                    .maxParticipants(10)
                    .currentParticipants(3)
                    .createDate(LocalDate.of(2024, 2, 21))
                    .activateStatus(StudyRoom.ActivateStatus.ACTIVATED)
                    .managerSequenceId(1)
                    .build());
        }

        @Override
        public void update(int roomId, String roomName, int maxParticipants, int currentParticipants, int managerSequenceId) {

        }

        @Override
        public List<StudyRoom> findByActivatedTrue() {
            return null;
        }
    }

    static class StubParticipantRepository implements ParticipantRepository {

        @Override
        public void save(int roomId, int seqId, LocalDateTime now) {

        }

        @Override
        public void delete(int roomId, int seqId) {

        }

        @Override
        public int countTotalParticipantsNum(int roomId) {
            return 0;
        }

        @Override
        public boolean isMemberExists(int roomId, int memberSequenceId) {
            return false;
        }

        @Override
        public List<Participant> findByRoomId(int roomId) {
            return List.of(
                    new Participant(1, 1, LocalDateTime.of(2024, 2, 21, 9, 1)),
                    new Participant(1, 2, LocalDateTime.of(2024, 2, 21, 9, 3)),
                    new Participant(1, 3, LocalDateTime.of(2024, 2, 21, 9, 3)));
        }
    }
}

