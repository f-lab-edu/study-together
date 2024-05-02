package dev.flab.studytogether.domain.room.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class Participant {
    private final int roomId;
    private final int memberSequenceId;
    private final LocalDateTime roomEntryTime;
}
