package dev.flab.simpleweather.domain.room;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class StudyRoomForm {
    private String roomName;
    private int total;
    private LocalDate createDate;

    public StudyRoomForm(String roomName, int total) {
        this.roomName = roomName;
        this.total = total;
        setCreateDate();
    }

    public String getRoomName() {
        return roomName;
    }

    public int getTotal() {
        return total;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public void setCreateDate() {
        this.createDate = LocalDate.now();
    }


}
