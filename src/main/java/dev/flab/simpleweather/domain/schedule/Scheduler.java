package dev.flab.simpleweather.domain.schedule;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Scheduler {

    private int schedulerSeq;
    private String date;
    private String seqId;
    private String id;

    public Scheduler(HttpSession httpSession, String date) {
        this.seqId = httpSession.getAttribute("seq_id").toString();
        this.id = httpSession.getAttribute("id").toString();
        this.date = date;
    }

    public Scheduler(int schedulerSeq, String date, String seqId, String id) {
        this.schedulerSeq = schedulerSeq;
        this.date = date;
        this.seqId = seqId;
        this.id = id;
    }

    public int getSchedulerSeq() {
        return schedulerSeq;
    }

    public void setSchedulerSeq(int schedulerSeq) {
        this.schedulerSeq = schedulerSeq;
    }

    public String getDate() {
        return date;
    }



    public String getSeqId() {
        return seqId;
    }


    public String getId() {
        return id;
    }




    public static Scheduler of(HttpSession httpSession, String date){

        return new Scheduler(httpSession, date);
    }
    public static Scheduler ofWithSeqID(int schedulerSeq, String date, String seqId, String id){
        return new Scheduler(schedulerSeq, date, seqId, id);
    }


}


