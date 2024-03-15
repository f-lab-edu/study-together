package dev.flab.studytogether.domain.member.service;

import static org.junit.jupiter.api.Assertions.*;

import dev.flab.studytogether.domain.member.entity.Member;
import dev.flab.studytogether.domain.member.exception.MemberNotFoundException;
import dev.flab.studytogether.domain.member.exception.PasswordMismatchException;
import dev.flab.studytogether.domain.member.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;

class MemberServiceTest {

    @Test
    @DisplayName("회원가입을 하면 Member 객체를 반환한다.")
    void whenMemberCreatedThenReturnsMember() {
        //given
        MemberService memberService = new MemberService(new FakeMemberRepository());

        String id = "memberID";
        String password = "123";
        String nickname = "memberNickname";

        //when
        Member resultMember = memberService.create(id, password, nickname);

        //then
        assertEquals(id, resultMember.getId());
        assertEquals(password, resultMember.getPassword());
        assertEquals(nickname, resultMember.getNickname());
    }

    @Test
    @DisplayName("존재하지 않는 ID로 로그인 하면 MemberNotFoundException 예외를 던진다.")
    void whenLoginWithNonExistsIdThenThrowsMemberNotFoundException() {
        //given
        MemberService memberService = new MemberService(new FakeMemberRepository());

        String invalidId = "memberID2";
        String password = "123";

        //when, then
        assertThrows(MemberNotFoundException.class, () ->
                memberService.login(invalidId, password));

    }

    @Test
    @DisplayName("올바르지 않은 password로 로그인 하면 PasswordMismatchException 예외를 던진다.")
    void whenLoginWithWrongPasswordThenThrowsPasswordMismatchException() {
        //given
        MemberService memberService = new MemberService(new FakeMemberRepository());

        String id = "memberID";
        String password = "123";
        String nickname = "memberNickname";

        memberService.create(id, password, nickname);

        String invalidPassword = "1234";

        //when, then
        assertThrows(PasswordMismatchException.class, () ->
                memberService.login(id, invalidPassword));

    }

    @Test
    @DisplayName("로그인에 성공하면 Member를 반환한다.")
    void whenLoginIsSucceedThenReturnsMember() {
        //given
        MemberService memberService = new MemberService(new FakeMemberRepository());

        String id = "memberID";
        String password = "123";
        String nickname = "memberNickname";

        memberService.create(id, password, nickname);

        //when
        Member resultMember = memberService.login(id, password);

        //then
        assertEquals(id, resultMember.getId());
        assertEquals(password, resultMember.getPassword());
    }

    @Test
    @DisplayName("ID가 존재하면 true를 반환한다.")
    void whenIdExistsThenReturnsTrue() {
        //given
        MemberService memberService = new MemberService(new FakeMemberRepository());

        String id = "memberID";
        String password = "123";
        String nickname = "memberNickname";

        memberService.create(id, password, nickname);

        //when, then
        assertTrue(memberService.isIdExists(id));
    }

    @Test
    @DisplayName("ID가 존재하지 않으면 false를 반환한다.")
    void whenIdNotExistsThenReturnsFalse() {
        //given
        MemberService memberService = new MemberService(new FakeMemberRepository());

        //when, then
        assertFalse(memberService.isIdExists("memberId"));

    }

    static class FakeMemberRepository implements MemberRepository {

        private final Collection<Member> fakeMembers = new ArrayList<>();

        @Override
        public Member save(Member member) {

            Member newMember = Member.createWithSequenceId(
                    getMaxSequenceID(),
                    member.getId(),
                    member.getPassword(),
                    member.getNickname());

            fakeMembers.add(newMember);
            return newMember;
        }

        @Override
        public Optional<Member> findByID(String id) {
            return fakeMembers.stream()
                    .filter(member -> member.getId().equals(id))
                    .findFirst();
        }

        @Override
        public boolean isIdExists(String id) {
            for (Member member : fakeMembers) {
                if (member.getId().equals(id)) {
                    return true;
                }
            }
            return false;
        }

        private int getMaxSequenceID() {
            Optional<Member> memberWithMaxSequenceId = fakeMembers.stream()
                    .max(Comparator.comparingInt(Member::getSequenceId));

            return memberWithMaxSequenceId.map(member -> member.getSequenceId() + 1).orElse(1);
        }
    }
}