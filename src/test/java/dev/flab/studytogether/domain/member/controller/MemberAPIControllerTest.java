package dev.flab.studytogether.domain.member.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.flab.studytogether.domain.member.dto.MemberCreateRequestDto;
import dev.flab.studytogether.domain.member.entity.Member;
import dev.flab.studytogether.domain.member.service.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@ExtendWith(MockitoExtension.class)
class MemberAPIControllerTest {
    @InjectMocks
    MemberAPIController memberAPIController;
    @Mock
    MemberService memberService;
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    
    @BeforeEach
    void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(memberAPIController).build();
        this.objectMapper = new ObjectMapper();
    }
    
    private String toJsonString(Object object) throws JsonProcessingException {
        return this.objectMapper.writeValueAsString(object);
    }
    
    @Test
    @DisplayName("POST /api/members/join api 메서드 호출 확인, http 응답 확인, 서비스 레이어의 비즈니스 로직 호출 확인 테스트")
    void memberJoinTest() throws Exception {
        //given
        int sequenceId = 1;
        String id = "myTestId";
        String password = "testPassword";
        String nickname = "testNickName";

        MemberCreateRequestDto memberCreateRequestDto =
                new MemberCreateRequestDto(id, password, nickname);

        Member mockMember =
                Member.createWithSequenceId(sequenceId, id, password, nickname);

        given(memberService.create(id, password, nickname))
                .willReturn(mockMember);

        //when, then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/members/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJsonString(memberCreateRequestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sequenceId").value(sequenceId))
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.nickname").value(nickname))
                .andDo(print());
        
        verify(memberService, Mockito.times(1)).create(id, password, nickname);
    }
    
    @Test
    @DisplayName("POST /api/members/login api 메서드 호출 확인, http 응답 확인, 서비스 레이어의 비즈니스 로직 호출 확인 테스트")
    void memberLoginTest() throws Exception {
        //given
        int sequenceId = 1;
        String id = "myTestId";
        String password = "testPassword";
        String nickname = "testNickName";

        MockHttpSession httpSession = new MockHttpSession();

        Member mockMember =
                Member.createWithSequenceId(sequenceId, id, password, nickname);

        given(memberService.login(id, password))
                .willReturn(mockMember);
        
        //when
        mockMvc.perform(MockMvcRequestBuilders.post("/api/members/login")
                        .session(httpSession)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("id", id)
                        .param("password", password))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sequenceId").value(sequenceId))
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.nickname").value(nickname))
                .andDo(print());
        
        //then
        verify(memberService, Mockito.times(1)).login(id, password);
    }

    @Test
    @DisplayName("GET /api/members/checkDuplicate api 메서드 호출 확인, http 응답 확인, 서비스 레이어의 비즈니스 로직 호출 확인 테스트")
    void checkDuplicatedIdTest() throws Exception {
        //given
        String id = "testId";

        given(memberService.isIdExists(id)).willReturn(false);

        //when, then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/members/checkDuplicate")
                        .param("id", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());

        verify(memberService, Mockito.times(1)).isIdExists(id);
    }

    @Test
    @DisplayName("GET /api/members/logout 호출하면 세션의 seq_id 속성이 null이 된다")
    void memberLogoutTest() throws Exception {
        //given
        MockHttpSession httpSession = new MockHttpSession();
        httpSession.setAttribute("seq_id", 1);

        //when
        mockMvc.perform(MockMvcRequestBuilders.get("/api/members/logout")
                .session(httpSession));

        //then
        assertNull(httpSession.getAttribute("seq_id"));
    }
}