package dev.flab.simpleweather.domain.member;

public class Member {
    private int seqID;
    private String id;
    private String pw;
    private String nickname;

    public int getSeqID(){
        return seqID;
    }

    public void setSeqID(int seqID){
        this.seqID = seqID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getPw(){
        return pw;
    }

    public void setPw(String pw){
        this.pw = pw;
    }

    public String getNickname(){
        return nickname;
    }

    public void setNickname(String nickname){
        this.nickname = nickname;
    }
}
