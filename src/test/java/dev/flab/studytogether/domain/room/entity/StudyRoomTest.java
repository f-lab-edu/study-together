package dev.flab.studytogether.domain.room.entity;

import org.junit.jupiter.api.BeforeEach;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.*;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class StudyRoomTest {
    private StudyRoom studyRoom;
    @BeforeEach
    void setup() {
        Participant participant1 = new Participant(1, 1, ParticipantRole.ROOM_MANAGER, LocalDateTime.of(2024, 2, 21, 9, 1));
        Participant participant2 = new Participant(1, 2, ParticipantRole.ORDINARY_PARTICIPANT, LocalDateTime.of(2024, 2, 21, 9, 3));
        Participant participant3 = new Participant(1, 3, ParticipantRole.ORDINARY_PARTICIPANT, LocalDateTime.of(2024, 2, 21, 9, 3));

        this.studyRoom = StudyRoom.builder()
                .roomId(1)
                .roomName("Test Room")
                .maxParticipants(4)
                .roomCreateDateTime(LocalDateTime.of(2024, 1, 31, 9, 1))
                .participants(new Participants(List.of(participant1, participant2, participant3)))
                .activateStatus(ActivateStatus.ACTIVATED)
                .build();
    }

    @Test
    @DisplayName("StudyRoom에 입장하면 현재 참여자 수가 1 증가한다.")
    void whenEnterRoomThenParticipantCountIncreases() {
        //given
        int currentParticipantCount = studyRoom.getCurrentParticipantsCount();

        long roomId = 1;
        int memberSequenceId = 4;
        ParticipantRole participantRole = ParticipantRole.ORDINARY_PARTICIPANT;
        LocalDateTime entryTime = LocalDateTime.of(2024, 2, 2, 9, 1);

        Participant enterParticipant
                = new Participant(roomId, memberSequenceId, participantRole, entryTime);

        //when
        studyRoom.enterRoom(enterParticipant);

        //then
        assertEquals(currentParticipantCount + 1, studyRoom.getCurrentParticipantsCount());
    }

    @Test
    @DisplayName("StudyRoom에서 퇴장하면 현재 참여자 수가 1 감소한다.")
    void whenExitRoomThenParticipantCountDecreases() {
        //given
        int currentParticipantCount = studyRoom.getCurrentParticipantsCount();

        long roomId = 1;
        int memberSequenceId = 3;
        ParticipantRole participantRole = ParticipantRole.ORDINARY_PARTICIPANT;
        LocalDateTime entryTime = LocalDateTime.of(2024, 2, 21, 9, 3);

        Participant exitParticipant = new Participant(roomId, memberSequenceId, participantRole, entryTime);

        //when
        studyRoom.exitRoom(exitParticipant);

        //then
        assertEquals(currentParticipantCount - 1, studyRoom.getCurrentParticipantsCount());
    }

    @Test
    @DisplayName("StudyRoom의 현재 정원이 차지 않았다면 False를 반환한다.")
    void whenStudyRoomIsNotFullThenReturnsFalse() {
        //when, then
        assertFalse(studyRoom.isRoomFull());
    }

    @Test
    @DisplayName("Room Manager 변경 테스트")
    void changeRoomManagerTest() {
        //given
        Participant expectedNextRoomManager
                = new Participant(1, 2, ParticipantRole.ROOM_MANAGER, LocalDateTime.of(2024, 2, 21, 9, 3));

        //when
        studyRoom.changeRoomManager();

        //then
        assertTrue(expectedNextRoomManager.equals(studyRoom.getRoomManager()),
                "Expected: " + expectedNextRoomManager + ", but was: " + studyRoom.getRoomManager());
    }

    @Test
    @DisplayName("참여자가 Room Manager일 경우 True를 반환한다.")
    void whenParticipantIsRoomManagerThenReturnsTrue() {
        //given
        Participant participant = new Participant(1, 1, ParticipantRole.ROOM_MANAGER, LocalDateTime.of(2024, 2, 21, 9, 1));

        //when, then
        assertTrue(studyRoom.isRoomManager(participant));
    }

    @Test
    @DisplayName("참여자가 Room Manager 아닐 경우 False를 반환한다.")
    void whenParticipantIsNotRoomManagerThenReturnsFalse() {
        //given
        Participant participant = new Participant(1, 2, ParticipantRole.ORDINARY_PARTICIPANT, LocalDateTime.of(2024, 2, 21, 9, 3));

        //when, then
        assertFalse(studyRoom.isRoomManager(participant));
    }
    
    @Test
    @DisplayName("해당 참여자가 Study Room에 존재할 경우 True를 반환한다.")
    void whenMemberExistsThenReturnsTrue() {
        //given
        Participant participant = new Participant(1, 2, ParticipantRole.ORDINARY_PARTICIPANT, LocalDateTime.of(2024, 2, 21, 9, 3));
        
        //when, then
        assertTrue(studyRoom.isMemberExists(participant));
    }
    
    @Test
    @DisplayName("해당 참여자가 Study Room에 존재할 경우 False를 반환한다.")
    void whenMemberNotExistsThenReturnsFalse() {
        //given
        Participant nonExistentParticipant = new Participant(1, 4, ParticipantRole.ORDINARY_PARTICIPANT, LocalDateTime.of(2024, 2, 1, 2,2));
        
        //when, then
        assertFalse(studyRoom.isMemberExists(nonExistentParticipant));
    }

    @Test
    @DisplayName("Room Manager 권한을 위임할 사용자가 존재하지 않으면 NoSuchElementException를 던진다.")
    void whenNoNextManagerExistsThenThrowsNoSuchElementException() {
        //given
        Participant participant = new Participant(1, 1, ParticipantRole.ROOM_MANAGER, LocalDateTime.of(2024, 2, 21, 9, 1));

        StudyRoom studyRoomWithoutNextManager = StudyRoom.builder()
                .roomId(1)
                .roomName("Test Room")
                .maxParticipants(4)
                .roomCreateDateTime(LocalDateTime.of(2024, 1, 31, 9, 1))
                .participants(new Participants(List.of(participant)))
                .activateStatus(ActivateStatus.ACTIVATED)
                .build();

        //when, then
        assertThatThrownBy(studyRoomWithoutNextManager::changeRoomManager)
                .isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining("방장 권한을 위임할 사용자가 존재하지 않습니다.");
    }
}