package dev.flab.studytogether.domain.member.repository;

import dev.flab.studytogether.domain.member.entity.Member;

import java.util.Optional;

public interface MemberRepository {
    Member save(Member member);
    Optional<Member> findByID(String id);
}
