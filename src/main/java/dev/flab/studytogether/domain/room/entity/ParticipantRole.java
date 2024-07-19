package dev.flab.studytogether.domain.room.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
public enum ParticipantRole {
    ROOM_MANAGER("Study Room Manager","방장"),
    ORDINARY_PARTICIPANT("Ordinary Participant", "일반 참여자");

    @Getter
    private final String roleName;
    private final String description;

    public static ParticipantRole findByRoleName(String roleName) {
        return Arrays.stream(ParticipantRole.values())
                .filter(e -> e.roleName.equals(roleName))
                .findFirst()
                .orElseThrow(()-> new IllegalArgumentException("No role matched"));
    }
}
