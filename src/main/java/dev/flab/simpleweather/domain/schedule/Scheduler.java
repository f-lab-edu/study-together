package dev.flab.simpleweather.domain.schedule;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Scheduler {

    private int schedulerSeq;
    private LocalDate date;
    private int seqId;
    private String id;


    public Scheduler(int schedulerSeq, LocalDate date, int seqId, String id) {
        this.schedulerSeq = schedulerSeq;
        this.date = date;
        this.seqId = seqId;
        this.id = id;
    }

    public int getSchedulerSeq() {
        return schedulerSeq;
    }

    public LocalDate getDate() {
        return date;
    }

    public int getSeqId() {
        return seqId;
    }

    public String getId() {
        return id;
    }




}


