package dev.flab.studytogether.domain.member.controller;

import dev.flab.studytogether.aop.PostMethodLog;
import dev.flab.studytogether.domain.member.dto.MemberResponse;
import dev.flab.studytogether.domain.member.entity.Member;
import dev.flab.studytogether.domain.member.service.MemberService;
import dev.flab.studytogether.utils.SessionUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import dev.flab.studytogether.domain.member.dto.MemberCreateRequestDto;

@RequestMapping("/api/members")
@Tag(name = "Member", description = "회원 관리 API")
@RestController
public class MemberAPIController {

    private final MemberService memberService;

    public MemberAPIController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/join")
    @PostMethodLog
    @Operation(summary = "Create member", description = "회원가입")
    @ResponseStatus(HttpStatus.OK)
    public MemberResponse join(@RequestBody MemberCreateRequestDto requestDto) {
        Member member = memberService.create(requestDto.getId(), requestDto.getPassword(), requestDto.getNickname());

        return new MemberResponse(member.getSequenceId(), member.getId(), member.getNickname());
    }

    @PostMapping("/login")
    @PostMethodLog
    @Operation(summary = "login", description = "로그인")
    @ResponseStatus(HttpStatus.OK)
    public MemberResponse login(
            @RequestParam String id,
            @RequestParam String password,
            HttpSession httpSession,
            HttpServletResponse response) {
        Member member = memberService.login(id, password);
        SessionUtil.setloginMemberSession(httpSession, member.getId(), member.getSequenceId());

        setCookies(response, id, member.getSequenceId());

        return new MemberResponse(member.getSequenceId(), member.getId(), member.getNickname());
    }

    @GetMapping("/checkDuplicate")
    @ResponseStatus(HttpStatus.OK)
    public boolean checkIdDuplicated(@RequestParam String id) {
        return memberService.isIdExists(id);
    }

    @GetMapping("/logout")
    @Operation(summary = "logout", description = "로그아웃")
    @ResponseStatus(HttpStatus.OK)
    public String logout(HttpSession httpSession) {
        SessionUtil.logoutMemebr(httpSession);

        return "logged out successfully.";
    }

    private void setCookies(HttpServletResponse response, String id, int sequenceId) {
        Cookie idCookie = new Cookie("memberID", id);
        idCookie.setPath("/");
        response.addCookie(idCookie);

        Cookie sequenceIDCookie = new Cookie("sequenceID", Integer.toString(sequenceId));
        sequenceIDCookie.setPath("/");
        response.addCookie(sequenceIDCookie);
    }

}







