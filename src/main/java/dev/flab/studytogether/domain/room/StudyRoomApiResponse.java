package dev.flab.studytogether.domain.room;

import dev.flab.studytogether.domain.room.entity.StudyRoom;

public class StudyRoomApiResponse {
    private int roomID;
    private String roomName;
    private int total;
    private StudyRoomApiResponse(int roomID, String roomName, int total) {
        this.roomID = roomID;
        this.roomName = roomName;
        this.total = total;
    }
    public static StudyRoomApiResponse from(StudyRoom studyRoom) {
        return new StudyRoomApiResponse(studyRoom.getRoomId(), studyRoom.getRoomName(), studyRoom.getTotal());
    }
    public int getRoomID() {
        return roomID;
    }
    public String getRoomName() {
        return roomName;
    }
    public int getTotal() {
        return total;
    }

}
