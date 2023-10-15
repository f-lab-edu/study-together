package dev.flab.simpleweather.domain.member.repository;

import dev.flab.simpleweather.domain.member.entity.Member;

import java.util.Optional;

public interface MemberRepository {
    Member save(Member member);
    Optional<Member> findByID(String id);
}
