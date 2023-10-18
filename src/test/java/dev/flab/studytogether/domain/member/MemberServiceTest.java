package dev.flab.studytogether.domain.member;

import dev.flab.studytogether.domain.member.entity.Member;
import dev.flab.studytogether.domain.member.repository.MemberRepository;
import dev.flab.studytogether.domain.member.service.MemberService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

// TODO 단위테스트 테스트코드 작성중...
// Mocking = Test Double
class MemberServiceTest {
    private MemberRepository memberRepository = Mockito.mock(MemberRepository.class);
    private MemberService memberService = new MemberService(memberRepository);



    @Test
    void id와pw에_해당하는_멤버가_조회된_경우_회원객체가_반환된다() {
        String id = "inno_not_empty";
        String pw = "flab";

        given(memberRepository.findByID(id)).willReturn(Optional.of(Member.of(id, pw, "")));

        Optional<Member> member = Optional.ofNullable(memberService.login(id, pw));

        boolean result = member.isPresent();
        assertEquals(true, result);
    }

    @Test
    void id에_해당하는_멤버가_조회되지않은_경우_NoSuchElementException_에러를_던진다() {
        String id = "inno_empty";
        String pw = "flab";

        given(memberRepository.findByID(id)).willReturn(Optional.empty());

        Assertions.assertThrows(NoSuchElementException.class, () -> memberService.login(id,pw));


    }
    @Test
    void pw가_틀릴경우_NoSuchElementException_에러를_던진다(){
        String id = "inno_not_empty";
        String pw = "wrong_pw";

        given(memberRepository.findByID(id)).willReturn(Optional.of(Member.of(id, "correct_pw", "")));

        Assertions.assertThrows(NoSuchElementException.class, () -> memberService.login(id,pw));
    }




}