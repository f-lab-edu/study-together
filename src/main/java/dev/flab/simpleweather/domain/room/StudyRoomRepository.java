package dev.flab.simpleweather.domain.room;


import javax.servlet.http.HttpSession;

public interface StudyRoomRepository {

    int createRoom(StudyRoom studyRoom, HttpSession httpSession);
}
