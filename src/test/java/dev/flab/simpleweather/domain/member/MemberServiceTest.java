package dev.flab.simpleweather.domain.member;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

// TODO 단위테스트 테스트코드 작성중...
// Mocking = Test Double
class MemberServiceTest {
    private MemberRepository memberRepository = Mockito.mock(MemberRepository.class);
    private MemberService memberService = new MemberService(memberRepository);

    @Test
    void id와pw에_해당하는_멤버가_조회된_경우_옵셔널객체안에_회원객체가존재한다() {
        String id = "inno_not_empty";
        String pw = "flab";

        given(memberRepository.findByID(id)).willReturn(Optional.of(Member.of(id, pw, "")));

        Optional<Member> member = memberService.login(id, pw);

        boolean result = member.isPresent();

        assertEquals(true, result);
    }

    @Test
    void id와pw에_해당하는_멤버가_조회되지않은_경우_옵셔널객체안에_회원객체가존재하지않는다() {
        String id = "inno_empty";
        String pw = "flab";

        given(memberRepository.findByID(id)).willReturn(Optional.empty());

        Optional<Member> member = memberService.login(id, pw);

        boolean result = member.isPresent();

        assertEquals(false, result);
    }
}