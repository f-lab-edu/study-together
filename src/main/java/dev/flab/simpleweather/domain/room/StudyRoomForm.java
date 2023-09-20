package dev.flab.simpleweather.domain.room;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class StudyRoomForm {
    private String roomName;
    private int total;

    public StudyRoomForm(String roomName, int total) {
        this.roomName = roomName;
        this.total = total;
    }

    public String getRoomName() {
        return roomName;
    }

    public int getTotal() {
        return total;
    }
    /*
    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public void setTotal(int total) {
        this.total = total;
    }
     */
}
