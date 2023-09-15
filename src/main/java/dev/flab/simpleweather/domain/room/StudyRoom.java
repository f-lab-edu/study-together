package dev.flab.simpleweather.domain.room;

import dev.flab.simpleweather.domain.status.Status;

public class StudyRoom {

    private int roomId;
    private String roomName;
    private int total;
    private int participantsNum;
    private String createDate;
    private Status status;
    private int managerSeqId;

    private StudyRoom(String roomName, int total, String createDate, int managerSeqId){
        this.roomName = roomName;
        this.total = total;
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

    public String getCreateDate() {
        return createDate;
    }

    public Status getStatus() {
        return status;
    }

    public int getManagerSeqId() {
        return managerSeqId;
    }



    public static StudyRoom of(String roomName, int total, String createDate, int managerSeqId){
        return new StudyRoom(roomName, total, createDate, managerSeqId);
    }


}
