package dev.flab.studytogether.domain.member.entity;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

class MemberTest {

    @Test
    @DisplayName("Password가 일치하면 true를 반환한다.")
    void whenPasswordMatchesThenReturnTrue() {
        //given
        String id = "testID";
        String password = "testPW";
        String nickname = "testNickName";

        Member member = Member.createWithoutSequenceId(id, password, nickname);

        //when, then
        assertTrue(member.isPasswordMatched(password));
    }

    @Test
    @DisplayName("Password가 일치하지 않으면 false를 반환한다.")
    void whenPasswordDoesNotMatchesThenReturnFalse() {
        //given
        String id = "testID";
        String password = "testPW";
        String nickname = "testNickName";

        Member member = Member.createWithoutSequenceId(id, password, nickname);

        String differentPassword = "differentPassword";

        //when, then
        assertFalse(member.isPasswordMatched(differentPassword));
    }

}