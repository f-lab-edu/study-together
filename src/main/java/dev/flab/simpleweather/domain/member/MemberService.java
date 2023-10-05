package dev.flab.simpleweather.domain.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class MemberService {

    private final MemberRepository memberRepository;


    public MemberService(MemberRepository memberRepository){
        this.memberRepository = memberRepository;
    }

    public int join(Member member){
        memberRepository.save(member);
        return member.getSeqID();
    }

    public Member login(String id, String pw){
        Member member = memberRepository.findByID(id).orElseThrow(()-> new NoSuchElementException());

        if(member.getPw().equals(pw)){
            return member;
        }
        else {
            throw new NoSuchElementException();
        }
    }



    public List<Member> findMembers(){
        return memberRepository.findAll();
    }
}
