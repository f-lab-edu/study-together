package dev.flab.simpleweather.domain.room;

public class StudyRoomApiResponse {
    private int roomID;
    public StudyRoomApiResponse(int roomID) {
        this.roomID = roomID;
    }

    public int getRoomID() {
        return roomID;
    }
}
