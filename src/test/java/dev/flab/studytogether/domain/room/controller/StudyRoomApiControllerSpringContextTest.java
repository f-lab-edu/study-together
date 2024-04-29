package dev.flab.studytogether.domain.room.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.flab.studytogether.domain.room.dto.RoomCreateRequest;
import dev.flab.studytogether.domain.room.entity.StudyRoom;
import dev.flab.studytogether.domain.room.exception.RoomEntryException;
import dev.flab.studytogether.domain.room.exception.RoomNotFoundException;
import dev.flab.studytogether.domain.room.service.StudyRoomExitService;
import dev.flab.studytogether.domain.room.service.StudyRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StudyRoomApiController.class)
class StudyRoomApiControllerSpringContextTest {
    @MockBean
    private StudyRoomService studyRoomService;
    @MockBean
    private StudyRoomExitService studyRoomExitService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    private String toJsonString(Object object) throws JsonProcessingException {
        return this.objectMapper.writeValueAsString(object);
    }

    @Test
    @DisplayName("POST /api/v1/rooms api 메소드 성공 응답 테스트")
    void createRoomApiMethodSuccessResponseTest() throws Exception {
        //given
        int memberSequenceId = 1;
        String roomName = "My Test Room";
        int totalParticipantsNumber = 10;

        MockHttpSession httpSession = new MockHttpSession();
        httpSession.setAttribute("seq_id", memberSequenceId);

        RoomCreateRequest roomCreateRequest = new RoomCreateRequest(roomName, totalParticipantsNumber);

        StudyRoom mockStudyRoom = StudyRoom.builder()
                .roomId(1)
                .roomName(roomName)
                .createDate(LocalDate.of(2023, 2, 25))
                .currentParticipants(0)
                .activateStatus(StudyRoom.ActivateStatus.ACTIVATED)
                .managerSequenceId(memberSequenceId)
                .maxParticipants(totalParticipantsNumber)
                .build();

        given(studyRoomService.createRoom(roomName, totalParticipantsNumber, memberSequenceId))
                .willReturn(mockStudyRoom);
        
        //when, then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/rooms")
                        .session(httpSession)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJsonString(roomCreateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.httpStatus").value("OK"))
                .andExpect(jsonPath("$.statusCode").value(200))
                .andExpect(jsonPath("$.message").value("OK"))
                .andExpect(jsonPath("$.data.roomId").value(mockStudyRoom.getRoomId()))
                .andExpect(jsonPath("$.data.roomName").value(mockStudyRoom.getRoomName()))
                .andExpect(jsonPath("$.data.maxParticipants").value(mockStudyRoom.getMaxParticipants()))
                .andExpect(jsonPath("$.data.currentParticipants").value(mockStudyRoom.getCurrentParticipants()))
                .andExpect(jsonPath("$.data.roomManagerSequenceId").value(mockStudyRoom.getManagerSequenceId()));
    }

    @Test
    @DisplayName("GET /api/v1/rooms api 메소드 RoomEntryException 예외 응답 테스트")
    void enterRoomApiMethodExceptionResponseTest() throws Exception {
        //given
        int roomId = 1;
        int memberSequenceId = 1;
        String errorMessage = "정원이 초과하여 입장 불가합니다";

        MockHttpSession httpSession = new MockHttpSession();
        httpSession.setAttribute("seq_id", memberSequenceId);

        given(studyRoomService.enterRoom(anyInt(), anyInt()))
                .willThrow(new RoomEntryException(errorMessage));

        //when, then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/rooms")
                        .session(httpSession)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("roomId", String.valueOf(roomId)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.httpStatus").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.statusCode").value(400))
                .andExpect(jsonPath("$.message").value(errorMessage))
                .andExpect(jsonPath("$.data").isEmpty());
    }
    
    @Test
    @DisplayName("DELETE /api/v1/rooms api 메소드 성공 응답 테스트")
    void exitRoomMethodApiSuccessResponseTest() throws Exception {
        //given
        int roomId = 1;
        int memberSequenceId = 1;
        MockHttpSession httpSession = new MockHttpSession();
        httpSession.setAttribute("seq_id", memberSequenceId);

        StudyRoom mockStudyRoom = StudyRoom.builder()
                .roomId(1)
                .roomName("My Test Room")
                .createDate(LocalDate.of(2023, 2, 25))
                .currentParticipants(2)
                .activateStatus(StudyRoom.ActivateStatus.ACTIVATED)
                .managerSequenceId(memberSequenceId)
                .maxParticipants(10)
                .build();

        given(studyRoomExitService.exitRoom(anyInt(), anyInt()))
                .willReturn(mockStudyRoom);


        //when, then
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/v1/rooms")
                        .param("roomId", String.valueOf(roomId))
                        .session(httpSession)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.httpStatus").value("OK"))
                .andExpect(jsonPath("$.statusCode").value(200))
                .andExpect(jsonPath("$.message").value("OK"))
                .andExpect(jsonPath("$.data.roomId").value(mockStudyRoom.getRoomId()))
                .andExpect(jsonPath("$.data.roomName").value(mockStudyRoom.getRoomName()))
                .andExpect(jsonPath("$.data.maxParticipants").value(mockStudyRoom.getMaxParticipants()))
                .andExpect(jsonPath("$.data.currentParticipants").value(mockStudyRoom.getCurrentParticipants()))
                .andExpect(jsonPath("$.data.roomManagerSequenceId").value(mockStudyRoom.getManagerSequenceId()))
                .andDo(print());
    }

    @Test
    @DisplayName("DELETE /api/v1/rooms api 메소드 RoomNotFoundException 예외 응답 테스트")
    void roomNotFoundExceptionResponseTest() throws Exception {
        //given가
        int roomId = 1;
        int memberSequenceId = 1;
        String errorMessage = "방이 존재하지 않습니다.";
        MockHttpSession httpSession = new MockHttpSession();
        httpSession.setAttribute("seq_id", memberSequenceId);

        given(studyRoomExitService.exitRoom(anyInt(), anyInt()))
                .willThrow(new RoomNotFoundException(errorMessage));

        //when, then
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/rooms")
                        .session(httpSession)
                        .contentType(MediaType.APPLICATION_JSON)
                        .session(httpSession)
                        .param("roomId", String.valueOf(roomId)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.httpStatus").value("NOT_FOUND"))
                .andExpect(jsonPath("$.statusCode").value(HttpStatus.NOT_FOUND.value()))
                .andExpect(jsonPath("$.message").value(errorMessage))
                .andExpect(jsonPath("$.data").isEmpty());
    }
}