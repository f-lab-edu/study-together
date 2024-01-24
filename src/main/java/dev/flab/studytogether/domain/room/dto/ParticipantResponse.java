package dev.flab.studytogether.domain.room.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ParticipantResponse {
    private final String memberID;
    private final int memberSequenceID;
}
