package dev.flab.studytogether.domain.member.repository;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import dev.flab.studytogether.domain.member.entity.Member;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.junit.jupiter.api.*;
import org.springframework.test.context.jdbc.Sql;
import java.util.Optional;

@JdbcTest
@Sql({"classpath:schema.sql", "classpath:test-data.sql"})
class MemberRepositoryTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private MemberRepository memberRepository;

    @BeforeEach
    void setup() {
        this.memberRepository = new MemberRepositoryImpl(jdbcTemplate);
    }

    @Test
    @DisplayName("Member를 save하면 sequenceID가 부여된 Member 객체를 반환한다.")
    void whenMemberIsSaved_thenReturnsMemberWithSequenceId() {
        //given
        String id = "testId";
        String password = "password123";
        String nickname = "testNickName";

        Member member = Member.createWithoutSequenceId(id, password, nickname);

        //when
        Member savedMember = memberRepository.save(member);

        //then
        assertNotNull(savedMember);
        assertThat(savedMember.getSequenceId()).isPositive();
    }
    
    @Test
    @DisplayName("Member를 저장할 때, Member 정보가 올바르게 저장되는지 확인")
    void shouldSaveMemberWithCorrectDetails() {
        //given
        String id = "testId";
        String password = "password123";
        String nickname = "testNickName";

        Member member = Member.createWithoutSequenceId(id, password, nickname);

        //when
        Member savedMember = memberRepository.save(member);

        //then
        assertEquals(member.getId(), savedMember.getId());
        assertEquals(member.getNickname(), savedMember.getNickname());
        assertEquals(member.getPassword(), savedMember.getPassword());
    }

    @Test
    @DisplayName("존재하는 회원 ID로 회원을 찾을 때, 해당 회원이 반환되어야 한다.")
    void whenFindMemberByExistingMemberID_thenReturnsTheMember() {
        //given
        //test-data.sql에 존재하는 데이터
        String memberId = "john_doe";

        //when
        Optional<Member> returnedMember = memberRepository.findByID(memberId);

        //then
        assertThat(returnedMember).isPresent();
    }

    @Test
    @DisplayName("존재하지 않는 회원 ID로 회원을 찾을때, Optional.empty()가 반환되어야 한다.")
    void whenFindMemberByNotExistingMemberId_thenReturnsEmpty() {
        //given
        //test-data.sql에 존재하지 않는 데이터
        String memberId = "notExistingId";

        //when
        Optional<Member> returnedMember = memberRepository.findByID(memberId);

        //then
        assertThat(returnedMember).isEmpty();
    }

    @Test
    @DisplayName("데이터베이스에 존재하는 아이디로 해당 아이디를 가진 회원 존재 여부를 확인하면 true를 반환한다.")
    void whenCheckIdExistsWithExistingMemberId_thenReturnsTrue() {
        //given
        //test-data.sql에 존재하는 데이터
        String memberId = "john_doe";

        //when
        boolean idExists = memberRepository.isIdExists(memberId);

        //then
        assertTrue(idExists);
    }

    @Test
    @DisplayName("데이터베이스에 존재하지 않는 아이디로 회원 존재 여부를 확인하면 false를 반환한다.")
    void whenCheckIdExistsWithNotExistingMemberId_thenReturnsFalse() {
        //given
        //test-data.sql에 존재하지 않는 데이터
        String memberId = "notExistingId";

        //when
        boolean idExists = memberRepository.isIdExists(memberId);

        //then
        assertFalse(idExists);
    }
}