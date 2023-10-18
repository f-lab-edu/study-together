package dev.flab.studytogether.domain.member.controller;

import dev.flab.studytogether.aop.PostMethodLog;
import dev.flab.studytogether.domain.member.entity.Member;
import dev.flab.studytogether.domain.member.service.MemberService;
import dev.flab.studytogether.utils.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.*;
import dev.flab.studytogether.domain.member.dto.MemberCreateRequestDto;

@Controller
public class MemberAPIController {

    private final MemberService memberService;

    @Autowired
    public MemberAPIController(MemberService memberService){
        this.memberService = memberService;
    }

    @PostMapping("/members/join")
    @PostMethodLog
    public String create(MemberCreateRequestDto requestDto){
        memberService.create(requestDto.toServiceDto());
        return "redirect:/login";
    }

    @PostMapping("/login")
    @PostMethodLog
    public String login(String id, String pw, HttpSession httpSession) {
        try {
            Member member = memberService.login(id, pw);
            SessionUtil.setloginMemberSession(httpSession, member.getId(), member.getSeqID());
        } catch (NoSuchElementException e) {
            return "redirect:/login";
        }
        return "main.html";
    }
    @GetMapping("/logout")
    public void logout(HttpSession httpSession){
        SessionUtil.logoutMemebr(httpSession);
    }


}







