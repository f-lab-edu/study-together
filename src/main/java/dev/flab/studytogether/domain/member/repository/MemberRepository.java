package dev.flab.studytogether.domain.member.repository;

import dev.flab.studytogether.domain.member.entity.Member;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface MemberRepository {
    Member save(Member member);
    Optional<Member> findByID(String id);
    boolean isIdExists(String id);
}
