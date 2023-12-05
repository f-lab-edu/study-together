package dev.flab.studytogether.domain.member.entity;

import lombok.Getter;

@Getter
public class Member {
    private int sequenceId;
    private final String id;
    private final String password;
    private final String nickname;

    private Member(int sequenceId, String id, String password, String nickname){
        this.sequenceId = sequenceId;
        this.id = id;
        this.password = password;
        this.nickname = nickname;
    }

    private Member(String id, String password, String nickname) {
        this.id = id;
        this.password = password;
        this.nickname = nickname;
    }

    public static Member createWithoutSequenceId(String id, String password, String nickname) {
        return new Member(id, password, nickname);
    }
    
    public static Member createWithSequenceId(int sequenceId, String id, String password, String nickname) {
        return new Member(sequenceId, id, password, nickname);
    }
    
}
