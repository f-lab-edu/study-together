package dev.flab.simpleweather.domain.member;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class MemberRepositoryImpl implements MemberRepository{

    private final JdbcTemplate jdbcTemplate;

    public MemberRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Member save(Member member) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("MEMBER").usingGeneratedKeyColumns("SEQ_ID");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("ID", member.getId());
        parameters.put("PW", member.getPw());
        parameters.put("NICKNAME", member.getNickname());

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        member.setSeqID(key.intValue());

        return member;
    }

    @Override
    public List<Member> findAll() {
        return jdbcTemplate.query("select * from member", memberRowMapper());

    }

    @Override
    public Optional<Member> findByID(String id) {

        try {
            Member member = jdbcTemplate.queryForObject("select * from member where id = ?", memberRowMapper(), id);
            return Optional.of(member);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    private RowMapper<Member> memberRowMapper(){
        return (rs, rowNum) -> new Member(
                rs.getInt("seq_id"),
                rs.getString("id"),
                rs.getString("pw"),
                rs.getString("nickname"));
    }

}
