package dev.flab.simpleweather.domain.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.*;


@RestController
public class MemberController {

    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService){
        this.memberService = memberService;
    }

    @PostMapping("/members/new")
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
        return "redirect:/";
    }

    public String toLoginPage(HttpSession session){
        String id = (String) session.getAttribute("id");
        //로그인된 상태
        if(id != null){
            return "redirect:/";
        }
        return "login";

    }
    @GetMapping("/members")
    public ResponseEntity<?> readAll() {
        List<Member> members = memberService.findMembers();

        Map<String, Object> response = new HashMap<>();
        //Map<String, Object> response = new HashMap<>();
        response.put("members", members);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/members/register")
    public String MemberRegister(){
        return "register.html";
    }


}
