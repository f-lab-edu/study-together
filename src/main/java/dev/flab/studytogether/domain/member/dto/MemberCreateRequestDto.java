package dev.flab.studytogether.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberCreateRequestDto {
    private String id;
    private String password;
    private String nickname;
}
