package dev.flab.studytogether.domain.room.service;


import dev.flab.studytogether.domain.room.entity.StudyRoom;
import dev.flab.studytogether.domain.room.repository.ParticipantRepository;
import dev.flab.studytogether.domain.room.repository.StudyRoomRepository;
import org.springframework.stereotype.Service;
import java.sql.SQLException;


@Service
public class StudyRoomService {

    private final StudyRoomRepository studyRoomRepository;
    private final ParticipantRepository participantRepository;
    public StudyRoomService(StudyRoomRepository studyRoomRepository, ParticipantRepository participantRepository) {
        this.studyRoomRepository = studyRoomRepository;
        this.participantRepository = participantRepository;
    }

    public StudyRoom createRoom(String roomName, int total, int memberSeqId) throws SQLException {
        StudyRoom studyRoom = studyRoomRepository.save(roomName, total, memberSeqId);
        participantRepository.save(studyRoom.getRoomId(),memberSeqId);
        return studyRoom;
    }

    public StudyRoom enterRoom(int roomId, int memberSeqId) throws SQLException {
        if(participantRepository.countTotalParticipantsNum(roomId) < studyRoomRepository.findTotalByRoomId(roomId)) {
            StudyRoom studyRoom = studyRoomRepository.findByRoomId(roomId).orElseThrow();
            participantRepository.save(studyRoom.getRoomId(), memberSeqId);
            return studyRoom;
        }
        return null;
    }

    public StudyRoom exitRoom(int roomId, int memberSeqId){
        StudyRoom studyRoom = studyRoomRepository.findByRoomId(roomId).orElseThrow();
        participantRepository.delete(studyRoom.getRoomId(), memberSeqId);
        return studyRoom;
    }


}
