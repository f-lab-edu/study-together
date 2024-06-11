package dev.flab.studytogether.fakerepositories;

import dev.flab.studytogether.domain.room.entity.ActivateStatus;
import dev.flab.studytogether.domain.room.entity.StudyRoom;
import dev.flab.studytogether.domain.room.repository.StudyRoomRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class FakeStudyRoomRepository implements StudyRoomRepository {
    private final Collection<StudyRoom> fakeStudyRooms = new ArrayList<>();

    @Override
    public StudyRoom save(StudyRoom studyRoom) {
        fakeStudyRooms.add(studyRoom);

        return studyRoom;
    }

    @Override
    public Optional<StudyRoom> findByRoomId(long roomId) {
        return fakeStudyRooms.stream()
                .filter(x -> x.getRoomId() == roomId)
                .findFirst();
    }

    @Override
    public void update(StudyRoom studyRoom) {
        Optional<StudyRoom> previousStudyRoom = findByRoomId(studyRoom.getRoomId());

        fakeStudyRooms.remove(previousStudyRoom.get());
        fakeStudyRooms.add(studyRoom);
    }

    @Override
    public List<StudyRoom> findByActivatedTrue() {
        return fakeStudyRooms.stream()
                .filter(x -> x.getActivateStatus().equals(ActivateStatus.ACTIVATED))
                .toList();
    }
}
