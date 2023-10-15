package dev.flab.simpleweather.domain.room.repository;

import dev.flab.simpleweather.domain.room.entity.StudyRoom;

import java.util.Optional;

public interface StudyRoomRepository {

    StudyRoom save(String roomName, int total, int memberSeqId);
    Optional<StudyRoom> findByRoomId(int roomId);
    int findTotalByRoomId(int roomId);
}
