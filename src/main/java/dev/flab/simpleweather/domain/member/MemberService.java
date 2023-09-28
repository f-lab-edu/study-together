package dev.flab.simpleweather.domain.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MemberService {

    private final MemberRepository memberRepository;


    public MemberService(MemberRepository memberRepository){
        this.memberRepository = memberRepository;
    }

    public String join(Member member){
        memberRepository.save(member);
        return member.getId();
    }

    public Optional<Member> login(String id, String pw){

        Optional<Member> optionalMember = memberRepository.findByID(id);

        if(optionalMember.isPresent() && optionalMember.get().getPw().equals(pw)){
            return Optional.of(optionalMember.get());
        }
        else{
            return optionalMember;
        }

    }

    public List<Member> findMembers(){
        return memberRepository.findAll();
    }
}
