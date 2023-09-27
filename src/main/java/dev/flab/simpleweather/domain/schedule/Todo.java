package dev.flab.simpleweather.domain.schedule;

import dev.flab.simpleweather.domain.member.Member;

public class Todo {

    private int todoSeq;
    private int schedulerSeq;
    private String todo;
    private CheckStatus checkStatus;

    private Todo(int schedulerSeq, String todo, CheckStatus checkStatus) {
        this.schedulerSeq = schedulerSeq;
        this.todo = todo;
        this.checkStatus = checkStatus;
    }

    public int getTodoSeq() {
        return todoSeq;
    }

    public int getSchedulerSeq() {
        return schedulerSeq;
    }

    public String getTodo() {
        return todo;
    }

    public CheckStatus getCheckStatus() {
        return checkStatus;
    }

    public static Todo of(int schedulerSeq, String todo, CheckStatus checkStatus) {
        return new Todo(schedulerSeq, todo, checkStatus);
    }

    public enum CheckStatus {
        CHECKED(true),
        UNCHECKED(false);

        private boolean status;
        private CheckStatus(boolean status) {
            this.status = status;
        }

        public boolean getStatus(){

            return status;
        }
    }


}
