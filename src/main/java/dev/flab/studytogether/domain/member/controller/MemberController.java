package dev.flab.studytogether.domain.member.controller;

import dev.flab.studytogether.aop.GetMethodLog;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MemberController {

    @GetMapping("/login")
    @GetMethodLog
    public String tologinPage() {
        return "login.html";
    }

    @GetMapping("/join")
    @GetMethodLog
    public String toJoinPage() {
        return "join.html";
    }
}
