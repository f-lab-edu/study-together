package dev.flab.studytogether.domain.room.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthDelegateDTO {
    private int roomID;
    private String delegatorID;
    private int delegatorSequenceID;
}
