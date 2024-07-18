package dev.flab.studytogether.domain.schedule.entity;

import dev.flab.studytogether.enums.CompleteStatus;

public class Todo {

    private long todoID;
    private int schedulerSeq;
    private String todoContent;
    private CompleteStatus completeStatus;

    public Todo(int schedulerSeq, String todoContent, CompleteStatus completeStatus) {
        this.schedulerSeq = schedulerSeq;
        this.todoContent = todoContent;
        this.completeStatus = completeStatus;
    }

    public Todo(long todoID, int schedulerSeq, String todoContent, CompleteStatus completeStatus) {
        this.todoID = todoID;
        this.schedulerSeq = schedulerSeq;
        this.todoContent = todoContent;
        this.completeStatus = completeStatus;
    }

    public long getTodoID() {
        return todoID;
    }

    public int getSchedulerSeq() {
        return schedulerSeq;
    }

    public String getTodoContent() {
        return todoContent;
    }

    public CompleteStatus getCompleteStatus() {
        return completeStatus;
    }

    public static Todo ofWithTodoID(long todoID,int schedulerSeq, String todo, CompleteStatus completeStatus) {
        return new Todo(todoID, schedulerSeq, todo, completeStatus);
    }

}
