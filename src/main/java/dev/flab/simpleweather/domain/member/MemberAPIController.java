package dev.flab.simpleweather.domain.member;



import dev.flab.simpleweather.aop.PostMethodLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.*;



@Controller
public class MemberAPIController {

    private final MemberService memberService;

    @Autowired
    public MemberAPIController(MemberService memberService){
        this.memberService = memberService;
    }

    @PostMapping("/members/join")
    @PostMethodLog
    public String create(MemberForm memberForm){

        Member member = Member.of(memberForm.getId(), memberForm.getPw(), memberForm.getNickname());
        memberService.join(member);

        return "redirect:/login";

    }

    @PostMapping("/login")
    @PostMethodLog
    public String login(String id, String pw, HttpSession session) {
        try {
            Member member = memberService.login(id, pw);
            session.setAttribute("id", member.getId());
            session.setAttribute("seq_id", member.getSeqID());
        } catch (NoSuchElementException e) {
            return "redirect:/login";
        }
        return "main.html";
    }

}







