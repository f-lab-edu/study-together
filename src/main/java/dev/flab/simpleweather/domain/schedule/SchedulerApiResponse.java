package dev.flab.simpleweather.domain.schedule;

import java.util.List;

public class SchedulerApiResponse {
    private int schedulerSeq;
    private List<String> todos;

    public SchedulerApiResponse(int schedulerSeq, List<String> todos) {
        this.schedulerSeq = schedulerSeq;
        this.todos = todos;
    }

    public SchedulerApiResponse() {
    }

    public int getSchedulerSeq() {
        return schedulerSeq;
    }

    public void setSchedulerSeq(int schedulerSeq) {
        this.schedulerSeq = schedulerSeq;
    }

    public List<String> getTodos() {
        return todos;
    }

    public void setTodos(List<String> todos) {
        this.todos = todos;
    }
}
