package dev.flab.simpleweather.domain.schedule;

public class SchedulerTodoApiResponse {
    private int schedulerSeq;
    private long todoID;
    private String todoContent;

    public SchedulerTodoApiResponse(int schedulerSeq, long todoID, String todoContent) {
        this.schedulerSeq = schedulerSeq;
        this.todoID = todoID;
        this.todoContent = todoContent;
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
}
