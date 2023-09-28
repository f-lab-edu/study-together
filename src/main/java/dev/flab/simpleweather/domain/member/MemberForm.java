package dev.flab.simpleweather.domain.member;

public class MemberForm {

    private String id;
    private String pw;
    private String nickname;

    public MemberForm(String id, String pw, String nickname) {
        this.id = id;
        this.pw = pw;
        this.nickname = nickname;
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



    public static MemberForm of(String id, String pw, String nickname){
        return new MemberForm(id, pw, nickname);
    }





}
