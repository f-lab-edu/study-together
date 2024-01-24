package dev.flab.studytogether.domain.room.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class Participant {
    private int roomId;
    private int seqId;
    // room 입장 시간
    private LocalDateTime entryTime;
}
