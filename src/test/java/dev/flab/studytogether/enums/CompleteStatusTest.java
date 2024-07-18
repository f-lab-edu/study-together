package dev.flab.studytogether.enums;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.*;

class CompleteStatusTest {

    @Test
    @DisplayName("findByStatus 메소드 테스트")
    void findByStatusTest() {
        //when, then
        assertThat(CompleteStatus.findByStatus(false))
                .isEqualTo(CompleteStatus.UNCOMPLETED);

    }
}