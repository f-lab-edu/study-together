package dev.flab.simpleweather.domain.member.service;

import dev.flab.simpleweather.domain.member.entity.Member;
import dev.flab.simpleweather.domain.member.repository.MemberRepository;
import org.springframework.stereotype.Service;
import dev.flab.simpleweather.domain.member.dto.MemberServiceDto;
import java.util.NoSuchElementException;

@Service
public class MemberService {

    private final MemberRepository memberRepository;


    public MemberService(MemberRepository memberRepository){
        this.memberRepository = memberRepository;
    }

    public int create(MemberServiceDto memberServiceDto){
        Member member = memberRepository.save(memberServiceDto.toEntity());
        return member.getSeqID();
    }

    public Member login(String id, String pw){
        Member member = memberRepository.findByID(id).orElseThrow(()-> new NoSuchElementException());

        if(member.getPw().equals(pw)){
            return member;
        }
        else {throw new NoSuchElementException();}
    }


}
