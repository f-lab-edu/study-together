package dev.flab.studytogether.enums;

import java.util.Arrays;

public enum CompleteStatus {
    COMPLETED(true),
    UNCOMPLETED(false);

    private final boolean statusValue;
    CompleteStatus(boolean statusValue) {
        this.statusValue = statusValue;
    }

    public boolean getStatus(){
        return statusValue;
    }

    public static CompleteStatus findByStatus(final boolean status){
        return Arrays.stream(CompleteStatus.values())
                .filter(e -> e.statusValue == status )
                .findFirst()
                .orElseThrow(IllegalStateException::new);
    }
}
