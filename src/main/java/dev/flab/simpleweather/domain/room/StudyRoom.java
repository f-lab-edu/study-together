package dev.flab.simpleweather.domain.room;


import dev.flab.simpleweather.system.Status;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class StudyRoom {

    private int roomId;
    private String roomName;
    private int total;
    private int participantsNum;
    private String createDate;
    private Status status;
    private int managerSeqId;

    private StudyRoom(String roomName, int total, HttpSession httpSession){
        this.roomName = roomName;
        this.total = total;
        setCreateDate();
        this.managerSeqId = (int)httpSession.getAttribute("seq_id");
    }


    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId){
        this.roomId = roomId;
    }

    public String getRoomName() {
        return roomName;
    }

    public int getTotal() {
        return total;
    }

    public int getParticipantsNum() {
        return participantsNum;
    }

    private void setCreateDate() {
        LocalDate now = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String formatedNow = now.format(formatter);

        this.createDate = formatedNow;

    }
    public String getCreateDate() {
        return createDate;
    }

    public Status getStatus() {
        return status;
    }

    public int getManagerSeqId(HttpSession httpSession) {
        return managerSeqId;
    }


    public static StudyRoom of(String roomName, int total, HttpSession httpSession){
        return new StudyRoom(roomName, total, httpSession);
    }


}
