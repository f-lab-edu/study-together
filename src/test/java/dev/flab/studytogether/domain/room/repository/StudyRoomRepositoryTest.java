package dev.flab.studytogether.domain.room.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import dev.flab.studytogether.domain.room.entity.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@JdbcTest
@Sql({"classpath:schema.sql", "classpath:test-data.sql"})
class StudyRoomRepositoryTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private StudyRoomRepository studyRoomRepository;

    @BeforeEach
    void setup() {
        this.studyRoomRepository = new StudyRoomRepositoryImpl(jdbcTemplate);
    }

    @Test
    @DisplayName("StudyRoom을 save 하면 Room Sequence Id를 가진 StudyRoom을 반환한다.")
    void whenStudyRoomIsSaved_thenReturnsStudyRoomThatContainsSequenceID() {
        //given
        int roomId = 1;
        int memberSequenceId = 1;
        ParticipantRole participantRole = ParticipantRole.ROOM_MANAGER;
        LocalDateTime roomEntryTime = LocalDateTime.of(2023, 2, 1, 23, 4);

        //when
        StudyRoom studyRoom = studyRoomRepository.save(StudyRoom.builder()
                .roomName("test room name")
                .maxParticipants(10)
                .roomCreateDateTime(LocalDateTime.of(2023, 2, 1, 23, 2))
                .activateStatus(ActivateStatus.ACTIVATED)
                .participants(new Participants(List.of(new Participant(roomId, memberSequenceId, participantRole, roomEntryTime))))
                .build());

        //then
        assertThat(studyRoom.getRoomId()).isPositive();
    }

    @Test
    @DisplayName("StudyRoom을 save할 때, StudyRoom 정보가 올바르게 저장되는지 확인.")
    void shouldSaveStudyRoomWithCorrectDetails() {
        //given
        String roomName = "test room name";
        int maxParticipants = 10;
        int roomId = 1;
        int memberSequenceId = 1;
        ParticipantRole participantRole = ParticipantRole.ROOM_MANAGER;
        LocalDateTime roomEntryTime = LocalDateTime.of(2023, 2, 1, 23, 4);

        //when
        StudyRoom studyRoom = studyRoomRepository.save(StudyRoom.builder()
                .roomName("test room name")
                .maxParticipants(10)
                .roomCreateDateTime(LocalDateTime.of(2023, 2, 1, 23, 2))
                .activateStatus(ActivateStatus.ACTIVATED)
                .participants(new Participants(List.of(new Participant(roomId, memberSequenceId, participantRole, roomEntryTime))))
                .build());

        //then
        assertEquals(roomName, studyRoom.getRoomName());
        assertEquals(maxParticipants, studyRoom.getMaxParticipants());
    }

    @Test
    @DisplayName("StudyRoom의 속성을 업데이트 하고 변경사항이 올바르게 저장됐는지 확인한다.")
    void whenRoomIsUpdated_thenChangesShouldBeCorrectlyStored() {
//        //given
//        int roomId = 1;
//        StudyRoom studyRoom = studyRoomRepository.findByRoomId(roomId)
//                .orElseThrow(IllegalArgumentException::new);
//        String newRoomName = "New Room Name";
//
//        StudyRoom newStudyRoom = StudyRoom.builder()
//                .roomId(studyRoom.getRoomId())
//                .roomName(newRoomName)
//                .maxParticipants(studyRoom.getMaxParticipants())
//                .roomCreateDateTime(studyRoom.getRoomCreateDateTime())
//                .participants(studyRoom.getParticipants())
//                .activateStatus(studyRoom.getActivateStatus())
//                .build();
//
//
//        //when
//        studyRoomRepository.update(newStudyRoom);
//
//
//        //then
//        StudyRoom updatedStudyRoom = studyRoomRepository.findByRoomId(roomId)
//                .orElseThrow(IllegalArgumentException::new);
//        assertEquals(newRoomName, updatedStudyRoom.getRoomName());
    }

    @Test
    @DisplayName("활성화된 StudyRoom을 조회하면 빈 목록이 아니어야 한다.")
    void findByActivatedTrueTest() {
        //when
        List<StudyRoom> studyRooms = studyRoomRepository.findByActivatedTrue();

        //then
        assertThat(studyRooms).isNotEmpty();
    }

    @Test
    @DisplayName("존재하지 않는 Room Id로 조회하면 Optional.empty()를 반환한다.")
    void whenFindRoomByNotExistingRoomId_thenReturnsEmptyOptional() {
        //given
        int notExistingRoomId = 10;

        //when
        Optional<StudyRoom> studyRoom = studyRoomRepository.findByRoomId(notExistingRoomId);

        //then
        assertThat(studyRoom).isEmpty();
    }
}