package dev.flab.studytogether.domain.member.controller;

import dev.flab.studytogether.aop.PostMethodLog;
import dev.flab.studytogether.domain.member.entity.Member;
import dev.flab.studytogether.domain.member.service.MemberService;
import dev.flab.studytogether.utils.SessionUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
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
    public String join(MemberCreateRequestDto requestDto) {
        memberService.createMember(requestDto.toServiceDto());
        return "redirect:/login";
    }

    @PostMapping("/login")
    @PostMethodLog
    @Operation(summary = "login", description = "로그인")
    public String login(String id, String pw, HttpSession httpSession) {
        try {
            Member member = memberService.login(id, pw);
            SessionUtil.setloginMemberSession(httpSession, member.getId(), member.getSeqID());
        } catch (NoSuchElementException e) {
            return "redirect:/login";
        }
        return "main.html";
    }

    @GetMapping("/checkDuplicate/{id}")
    public boolean checkIdDuplicated(@PathVariable String id) {
        return memberService.isIdExists(id);
    }

    @GetMapping("/logout")
    @Operation(summary = "logout", description = "로그아웃")
    public void logout(HttpSession httpSession) {
        SessionUtil.logoutMemebr(httpSession);
    }


}







