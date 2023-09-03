package dev.flab.simpleweather.domain.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
public class MemberController {

    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService){
        this.memberService = memberService;
    }

    @GetMapping("/members")
    public ResponseEntity<?> readAll() {
        List<Member> members = memberService.findMembers();

        Map<String, Object> response = new HashMap<>();
        //Map<String, Object> response = new HashMap<>();
        response.put("members", members);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
