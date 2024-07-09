package dev.flab.studytogether.domain.room.entity;

import java.util.Arrays;

public enum ActivateStatus {
    ACTIVATED(true),
    TERMINATED(false);

    private final boolean statusValue;

    ActivateStatus(boolean statusValue) {
        this.statusValue = statusValue;
    }

    public boolean getStatusValue() {
        return statusValue;
    }


    public static ActivateStatus findByStatus(final boolean status) {
        return Arrays.stream(ActivateStatus.values())
                .filter(e -> e.statusValue == status)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
