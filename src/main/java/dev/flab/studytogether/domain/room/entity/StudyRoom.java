package dev.flab.studytogether.domain.room.entity;

import java.time.LocalDate;
import java.util.Arrays;

public class StudyRoom {

    private int roomId;
    private String roomName;
    private int total;
    private LocalDate createDate;
    private ActivateStatus activateStatus;
    private int managerSeqId;

    public static class Builder{
        private int roomId;
        private String roomName;
        private int total;
        private LocalDate createDate;
        private ActivateStatus activateStatus;
        private int managerSeqId;

        public Builder(){}

        public Builder roomID(int roomId){
            this.roomId = roomId;
            return this;
        }

        public Builder roomName(String roomName){
            this.roomName = roomName;
            return this;
        }

        public Builder total(int total){
            this.total = total;
            return this;
        }

        public Builder createDate(LocalDate createDate){
            this.createDate = createDate;
            return this;
        }

        public Builder activateStatus(ActivateStatus activateStatus){
            this.activateStatus = activateStatus;
            return this;
        }

        public Builder managerSeqId(int managerSeqId){
            this.managerSeqId = managerSeqId;
            return this;
        }
        public StudyRoom build(){
            return new StudyRoom(this);
        }

    }

    private StudyRoom(Builder builder){
        roomId = builder.roomId;
        roomName = builder.roomName;
        total = builder.total;
        createDate = builder.createDate;
        activateStatus = builder.activateStatus;
        managerSeqId = builder.managerSeqId;
    }

    public int getRoomId() {
        return roomId;
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

    public ActivateStatus getActivateStatus() {
        return activateStatus;
    }

    public int getManagerSeqId() {
        return managerSeqId;
    }


    public enum ActivateStatus{
        ACTIVATED(true),
        TERMINATED(false);

        private boolean statusValue;

        ActivateStatus(boolean statusValue) {
            this.statusValue = statusValue;
        }

        public boolean getStatusValue(){
            return statusValue;
        }


        public static ActivateStatus findByStatus(final boolean status) {
            return Arrays.stream(ActivateStatus.values())
                    .filter(e -> e.statusValue == status)
                    .findFirst()
                    .orElseThrow(IllegalStateException::new);
        }
    }




}
