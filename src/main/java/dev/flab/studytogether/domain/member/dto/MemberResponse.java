package dev.flab.studytogether.domain.member.dto;

import lombok.Data;

@Data
public class MemberResponse {
    private final int sequenceId;
    private final String id;
    private final String nickname;
}
