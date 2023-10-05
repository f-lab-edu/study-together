package dev.flab.simpleweather.domain.member;

import dev.flab.simpleweather.aop.GetMethodLog;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MemberController {

    @GetMapping("/login")
    public String tologinPage() {
        return "login.html";
    }

    @GetMapping("/join")
    public String toJoinPage() {
        return "join.html";
    }
}
