package dev.flab.simpleweather.domain.schedule.dto;

import java.time.LocalDate;

public class SchedulerTodoServiceDto {
    private int schedulerSeq;
    private long todoID;
    private String todoContent;
    private int memberSeqId;
    private LocalDate localDate;

    public static class Builder{
        private int schedulerSeq;
        private long todoID;
        private String todoContent;
        private int memberSeqId;
        private LocalDate localDate;


        public Builder(){}
        public Builder schedulerSeq(int schedulerSeq){
            this.schedulerSeq = schedulerSeq;
            return this;
        }
        public Builder todoID(long todoID){
            this.todoID = todoID;
            return this;
        }

        public Builder todoContent(String todoContent){
            this.todoContent = todoContent;
            return this;
        }

        public Builder memberSeqId(int memberSeqId){
            this.memberSeqId = memberSeqId;
            return this;
        }

        public Builder localDate(LocalDate localDate){
            this.localDate = localDate;
            return this;
        }

        public SchedulerTodoServiceDto build() { return new SchedulerTodoServiceDto(this); }
    }
    private SchedulerTodoServiceDto(Builder builder){
        schedulerSeq = builder.schedulerSeq;
        todoID = builder.todoID;
        todoContent = builder.todoContent;
        memberSeqId = builder.memberSeqId;
        localDate = builder.localDate;
    }

    public int getSchedulerSeq() {
        return schedulerSeq;
    }
    public long getTodoID() {
        return todoID;
    }

    public String getTodoContent() {
        return todoContent;
    }
    public int getMemberSeqId() {
        return memberSeqId;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }
}
