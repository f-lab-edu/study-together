package dev.flab.simpleweather.domain.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.*;


@Controller
public class MemberController {

    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService){
        this.memberService = memberService;
    }

    @PostMapping("/members/join")
    @ResponseBody
    public ResponseEntity<?> create(MemberForm memberForm){

        Member member = Member.of(memberForm.getId(), memberForm.getPw(), memberForm.getNickname());
        memberService.join(member);

        return new ResponseEntity<>(HttpStatus.OK);

    }
    @PostMapping("/login")
    public String login(String id, String pw, HttpSession session){
        Optional<Member> optionalMember = memberService.login(id, pw);
        if(optionalMember.isEmpty()){
            return "redirect:/login";
        }
        session.setAttribute("id", optionalMember.get().getId());
        session.setAttribute("seq_id", optionalMember.get().getSeqID());

        return "redirect:/login";
    }


    @RequestMapping("/login")
    public String toLoginPage(HttpSession session){
        String id = (String) session.getAttribute("id");
        //로그인된 상태
        if(id != null){
            return "main.html";
        }
        return "login.html";

    }
    @GetMapping("/members")
    public ResponseEntity<?> readAll() {
        List<Member> members = memberService.findMembers();

        Map<String, Object> response = new HashMap<>();
        //Map<String, Object> response = new HashMap<>();
        response.put("members", members);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping("/members/join")
    public String MemberRegister(){
        return "join.html";
    }


}
