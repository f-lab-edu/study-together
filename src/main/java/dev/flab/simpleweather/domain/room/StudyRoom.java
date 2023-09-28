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
    private LocalDate createDate;
    private Status status;
    private int managerSeqId;

    public StudyRoom(String roomName, int total, int managerSeqId, LocalDate createDate){
        this.roomName = roomName;
        this.total = total;
        this.managerSeqId = managerSeqId;
        this.createDate = createDate;
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


    public LocalDate getCreateDate() {
        return createDate;
    }

    public Status getStatus() {
        return status;
    }

    public int getManagerSeqId(int managerSeqId) {
        return managerSeqId;
    }





}
