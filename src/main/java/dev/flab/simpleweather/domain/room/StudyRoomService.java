package dev.flab.simpleweather.domain.room;

import dev.flab.simpleweather.domain.member.Member;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
public class StudyRoomService {

    private final StudyRoomRepository studyRoomRepository;


    public StudyRoomService(StudyRoomRepository studyRoomRepository) {
        this.studyRoomRepository = studyRoomRepository;
    }

    public int create(StudyRoom studyRoom, HttpSession httpSession){
        studyRoomRepository.createRoom(studyRoom, httpSession);
        return studyRoom.getRoomId();
    }
}
