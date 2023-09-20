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

@Controller
public class StudyRoomController {

    private final StudyRoomService studyRoomService;

    @Autowired
    public StudyRoomController(StudyRoomService studyRoomService) {
        this.studyRoomService = studyRoomService;
    }

    @RequestMapping("/studyrooms")
    public String toStudyRoomMain(HttpSession httpSession){
        String id = (String) httpSession.getAttribute("id");
        //로그인된 상태
        if(id != null){
            return "redirect:/studyroom_main.html";
        }
        return "login.html";

    }
    @PostMapping("/api/v1/rooms")
    @ResponseBody
    public ResponseEntity<Message> create(StudyRoomForm studyRoomForm, HttpSession httpSession){

        StudyRoom studyRoom = StudyRoom.of(studyRoomForm.getRoomName(), studyRoomForm.getTotal(), httpSession);

        studyRoomService.create(studyRoom, httpSession);

        Message message = new Message();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        message.setStatus(Message.StatusEnum.OK);
        message.setData(studyRoom);

        return new ResponseEntity<>(message, HttpStatus.OK);
    }

}
