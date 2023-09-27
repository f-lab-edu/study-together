package dev.flab.simpleweather.domain.room;

import dev.flab.simpleweather.system.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.nio.charset.Charset;

@RestController
public class StudyRoomController {

    private final StudyRoomService studyRoomService;

    @Autowired
    public StudyRoomController(StudyRoomService studyRoomService) {
        this.studyRoomService = studyRoomService;
    }

    @PostMapping("/api/v1/rooms")
    public StudyRoomApiResponse create(StudyRoomForm studyRoomForm, HttpSession httpSession){

        StudyRoom studyRoom = new StudyRoom(studyRoomForm.getRoomName(),
                studyRoomForm.getTotal(),
                (int)httpSession.getAttribute("seq_id"),
                studyRoomForm.getCreateDate());

        int roomID = studyRoomService.create(studyRoom, httpSession);

        return new StudyRoomApiResponse(roomID);
    }

}
