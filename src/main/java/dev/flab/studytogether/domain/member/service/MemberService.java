package dev.flab.studytogether.domain.member.service;

import dev.flab.studytogether.domain.member.entity.Member;
import dev.flab.studytogether.domain.member.exception.MemberNotFoundException;
import dev.flab.studytogether.domain.member.exception.PasswordMismatchException;
import dev.flab.studytogether.domain.member.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class MemberService {

    private final MemberRepository memberRepository;


    public MemberService(MemberRepository memberRepository){
        this.memberRepository = memberRepository;
    }

    public Member create(String id, String password, String nickname){
        return memberRepository.save(Member.createWithoutSequenceId(id, password, nickname));
    }

    public Member login(String id, String pw){
        Member member = memberRepository.findByID(id).orElseThrow(()-> new MemberNotFoundException("일치하는 ID가 없습니다."));

        if(member.getPassword().equals(pw)){
            return member;
        }

        throw new PasswordMismatchException("비밀번호가 일치하지 않습니다");
    }


    public boolean isIdExists(String id) {
        return memberRepository.isIdExists(id);
    }
}
