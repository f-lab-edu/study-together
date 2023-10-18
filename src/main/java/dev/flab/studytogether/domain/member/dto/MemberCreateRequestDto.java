package dev.flab.studytogether.domain.member.dto;


public class MemberCreateRequestDto {

    private String id;
    private String pw;
    private String nickname;

    public MemberCreateRequestDto(String id, String pw, String nickname) {
        this.id = id;
        this.pw = pw;
        this.nickname = nickname;
    }

    public String getId() {
        return id;
    }

    public String getPw() {
        return pw;
    }

    public String getNickname() {
        return nickname;
    }

    public MemberServiceDto toServiceDto() {
        return new MemberServiceDto(id, pw, nickname);
    }

}
