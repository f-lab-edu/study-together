package dev.flab.simpleweather.domain.member.dto;

import dev.flab.simpleweather.domain.member.entity.Member;

public class MemberServiceDto {
    private String id;
    private String pw;
    private String nickname;

    public MemberServiceDto(String id, String pw, String nickname) {
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

    public Member toEntity() {
        return Member.of(id, pw, nickname);
    }
}
