package dev.flab.simpleweather.domain.schedule;

public class Todo {

    private int todoSeq;
    private int schedulerSeq;
    private int seqId;
    private String id;
    private String date;
    private String todo;
    private char check;

    public enum checkGroup {
        CHECKED("T"),
        UNCHECKED("F");

        private String status;
        private checkGroup( String status) {
            this.status = status;
        }

        public String getStatus(){
            return status;
        }
    }

}
