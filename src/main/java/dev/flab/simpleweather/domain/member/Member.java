package dev.flab.simpleweather.domain.member;

public class Member {
    private int seqID;
    private final String id;
    private final String pw;
    private final String nickname;

    private Member(String id, String pw, String nickname){
        this.id = id;
        this.pw = pw;
        this.nickname = nickname;
    }

    private Member(int seqID, String id, String pw, String nickname){
        this.seqID = seqID;
        this.id = id;
        this.pw = pw;
        this.nickname = nickname;
    }
    public int getSeqID(){
        return seqID;
    }
    public void setSeqID(int seqID){
        this.seqID = seqID;
    }
    public String getId() {
        return id;
    }

    public String getPw(){
        return pw;
    }

    public String getNickname(){
        return nickname;
    }

    public static Member of(String id, String pw, String nickname) {
        return new Member(id, pw, nickname);
    }
    public static Member ofWithSeqID(int seqID, String id, String pw, String nickname) {
        return new Member(seqID, id, pw, nickname);
    }

}
