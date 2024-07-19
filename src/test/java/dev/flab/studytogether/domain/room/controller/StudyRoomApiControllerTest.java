package dev.flab.studytogether.domain.room.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import dev.flab.studytogether.domain.room.dto.RoomCreateRequest;
import dev.flab.studytogether.domain.room.entity.ActivateStatus;
import dev.flab.studytogether.domain.room.entity.StudyRoom;
import dev.flab.studytogether.domain.room.service.StudyRoomExitService;
import dev.flab.studytogether.domain.room.service.StudyRoomService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(MockitoExtension.class)
class StudyRoomApiControllerTest {
    @InjectMocks
    private StudyRoomApiController studyRoomApiController;
    @Mock
    private StudyRoomService studyRoomService;
    @Mock
    private StudyRoomExitService studyRoomExitService;
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(studyRoomApiController).build();
        this.objectMapper = new ObjectMapper();
    }

    private String toJsonString(Object object) throws JsonProcessingException {
        return this.objectMapper.writeValueAsString(object);
    }

    @Test
    @DisplayName("POST /api/v1/rooms api 메서드 호출 확인, http 응답 확인, 서비스 레이어의 비즈니스 로직 호출 확인 테스트")
    void createRoomTest() throws Exception {
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
                .roomCreateDateTime(LocalDateTime.of(2023, 2, 25, 17, 2))
                .activateStatus(ActivateStatus.ACTIVATED)
                .participants(new Participants(List.of(new Participant(1, 1, ParticipantRole.ROOM_MANAGER, LocalDateTime.of(2023, 2, 25, 17,2)))))
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
                .andExpect(jsonPath("$.roomId").value(mockStudyRoom.getRoomId()))
                .andExpect(jsonPath("$.roomName").value(mockStudyRoom.getRoomName()))
                .andExpect(jsonPath("$.maxParticipants").value(mockStudyRoom.getMaxParticipants()))
                .andExpect(jsonPath("$.currentParticipants").value(mockStudyRoom.getCurrentParticipantsCount()))
                .andExpect(jsonPath("$.roomManagerSequenceId").value(mockStudyRoom.getRoomManager().getMemberSequenceId()))
                .andDo(print());

        verify(studyRoomService, Mockito.times(1)).createRoom(roomName, totalParticipantsNumber, memberSequenceId);
    }
    
    @Test
    @DisplayName("POST /api/v1/rooms 주어진 매개 변수 형식이 잘못된 경우 결과로 BAD REQUEST, InvalidFormatException 반환")
    void createRoomMethodInvalidParameterFormatTest() throws Exception {
        //given
        int memberSequenceId = 1;
        String roomName = "My Test Room";
        String wrongValue = "cannotConvertToIntValue";

        String json = "{\"roomName\":\"" + roomName +"\",\"total\":\"" + wrongValue +"\"}";

        MockHttpSession httpSession = new MockHttpSession();
        httpSession.setAttribute("seq_id", memberSequenceId);



        //when, then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/rooms")
                .session(httpSession)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(result ->
                        result.getResolvedException().getClass().isAssignableFrom(InvalidFormatException.class))
                .andDo(print());
    }

    @Test
    @DisplayName("GET /api/v1/rooms api 메서드 호출 확인, http 응답 확인, 서비스 레이어의 비즈니스 로직 호출 확인 테스트")
    void roomEnterTest() throws Exception {
        //given
        int roomId = 1;
        int memberSequenceId = 2;
        MockHttpSession httpSession = new MockHttpSession();
        httpSession.setAttribute("seq_id", memberSequenceId);

        StudyRoom mockStudyRoom = StudyRoom.builder()
                .roomId(1)
                .roomName("My Test Room")
                .roomCreateDateTime(LocalDateTime.of(2023, 2, 25, 17, 2))
                .activateStatus(ActivateStatus.ACTIVATED)
                .participants(new Participants(List.of(
                        new Participant(roomId,
                                2,
                                ParticipantRole.ROOM_MANAGER,
                                LocalDateTime.of(2023, 2, 25, 17,2)),
                        new Participant(roomId,
                                memberSequenceId,
                                ParticipantRole.ORDINARY_PARTICIPANT,
                                LocalDateTime.of(2023, 2, 25, 19, 2))
                )))
                .maxParticipants(10)
                .build();

        given(studyRoomService.enterRoom(anyLong(), anyInt(), any()))
                .willReturn(mockStudyRoom);


        //when, then
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/rooms")
                        .param("roomId", String.valueOf(roomId))
                        .session(httpSession)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.roomId").value(mockStudyRoom.getRoomId()))
                .andExpect(jsonPath("$.roomName").value(mockStudyRoom.getRoomName()))
                .andExpect(jsonPath("$.maxParticipants").value(mockStudyRoom.getMaxParticipants()))
                .andExpect(jsonPath("$.currentParticipantsCount").value(mockStudyRoom.getCurrentParticipantsCount()))
                .andExpect(jsonPath("$.roomManagerSequenceId").value(mockStudyRoom.getRoomManager().getMemberSequenceId()))
                .andDo(print());

        verify(studyRoomService, Mockito.times(1)).enterRoom(anyLong(), anyInt(), any());
    }
    
    @Test
    @DisplayName("GET /api/v1/rooms 주어진 매개 변수 형식이 잘못된 경우 결과로 BAD REQUEST, InvalidFormatException 반환")
    void enterRoomMethodInvalidParameterFormatTest() throws Exception {
        //given
        String invalidFormatRoomId = "cannotConvertToIntValue";
        int memberSequenceId = 1;
        MockHttpSession httpSession = new MockHttpSession();
        httpSession.setAttribute("seq_id", memberSequenceId);
        
        //when, then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/rooms")
                        .session(httpSession)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("roomId", invalidFormatRoomId))
                .andExpect(status().isBadRequest())
                .andExpect(result ->
                        result.getResolvedException().getClass().isAssignableFrom(InvalidFormatException.class))
                .andDo(print());
    }

    @Test
    @DisplayName("DELETE /api/v1/rooms api 메서드 호출 확인, http 응답 확인, 서비스 레이어의 비즈니스 로직 호출 확인 테스트")
    void exitRoomTest() throws Exception {
        //given
        int roomId = 1;
        int memberSequenceId = 2;
        MockHttpSession httpSession = new MockHttpSession();
        httpSession.setAttribute("seq_id", memberSequenceId);

        StudyRoom mockStudyRoom = StudyRoom.builder()
                .roomId(1)
                .roomName("My Test Room")
                .roomCreateDateTime(LocalDateTime.of(2023, 2, 25, 17, 2))
                .activateStatus(ActivateStatus.ACTIVATED)
                .participants(new Participants(List.of(new Participant(1, 1, ParticipantRole.ROOM_MANAGER, LocalDateTime.of(2023, 2, 25, 17,2)))))
                .maxParticipants(10)
                .build();

        given(studyRoomExitService.exitRoom(anyLong(), anyInt()))
                .willReturn(mockStudyRoom);


        //when, then
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/v1/rooms")
                        .param("roomId", String.valueOf(roomId))
                        .session(httpSession)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.roomId").value(mockStudyRoom.getRoomId()))
                .andExpect(jsonPath("$.roomName").value(mockStudyRoom.getRoomName()))
                .andExpect(jsonPath("$.maxParticipants").value(mockStudyRoom.getMaxParticipants()))
                .andExpect(jsonPath("$.currentParticipantsCount").value(mockStudyRoom.getCurrentParticipantsCount()))
                .andExpect(jsonPath("$.roomManagerSequenceId").value(mockStudyRoom.getRoomManager().getMemberSequenceId()))
                .andDo(print());

        verify(studyRoomExitService,
                Mockito.times(1)).exitRoom(mockStudyRoom.getRoomId(), memberSequenceId);
    }

    @Test
    @DisplayName("DELETE /api/v1/rooms 주어진 매개 변수 형식이 잘못된 경우 결과로 BAD REQUEST, InvalidFormatException 반환")
    void exitRoomMethodInvalidParameterFormatTest() throws Exception {
        //given
        String invalidFormatRoomId = "cannotConvertToIntValue";
        int memberSequenceId = 1;
        MockHttpSession httpSession = new MockHttpSession();
        httpSession.setAttribute("seq_id", memberSequenceId);

        //when, then
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/rooms")
                        .session(httpSession)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("roomId", invalidFormatRoomId))
                .andExpect(status().isBadRequest())
                .andExpect(result ->
                        result.getResolvedException().getClass().isAssignableFrom(InvalidFormatException.class))
                .andDo(print());
    }

    @Test
    @DisplayName("GET /api/v1/rooms/activated api 메서드 호출 확인, http 응답 확인, 서비스 레이어의 비즈니스 로직 호출 확인 테스트")
    void getActivatedStudyRoomsTest() throws Exception {
        //given
        StudyRoom mockStudyRoom1 = StudyRoom.builder()
                .roomId(1)
                .roomName("My Test Room")
                .roomCreateDateTime(LocalDateTime.of(2023, 2, 25, 17, 2))
                .activateStatus(ActivateStatus.ACTIVATED)
                .participants(new Participants(List.of(new Participant(1, 1, ParticipantRole.ROOM_MANAGER, LocalDateTime.of(2023, 2, 25, 17,2)))))
                .maxParticipants(10)
                .build();


        StudyRoom mockStudyRoom2 = StudyRoom.builder()
                .roomId(2)
                .roomName("My Test Room2")
                .roomCreateDateTime(LocalDateTime.of(2023, 2, 25, 19, 3))
                .activateStatus(ActivateStatus.ACTIVATED)
                .participants(new Participants(List.of(new Participant(2, 2, ParticipantRole.ROOM_MANAGER, LocalDateTime.of(2023, 2, 25, 17,2)))))
                .maxParticipants(15)
                .build();

        List<StudyRoom> studyRooms = List.of(mockStudyRoom1, mockStudyRoom2);

        given(studyRoomService.getActivatedStudyRooms())
                .willReturn(studyRooms);

        // when
        ResultActions resultActions =
                mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/rooms/activated"));

        // then
        MvcResult mvcResult = resultActions
                .andExpect(status().isOk())
                .andReturn();

        verify(studyRoomService, Mockito.times(1)).getActivatedStudyRooms();

        //System.out.println("mvc result :" + mvcResult.getResponse().getContentAsString());
    }

    @Test
    @DisplayName("GET /api/v1/rooms/info api 메서드 호출 확인, http 응답 확인, 서비스 레이어의 비즈니스 로직 호출 확인 테스트")
    void getEnterAvailableStudyRoomsTest() throws Exception {
        //given
        StudyRoom mockStudyRoom1 = StudyRoom.builder()
                .roomId(1)
                .roomName("My Test Room")
                .roomCreateDateTime(LocalDateTime.of(2023, 2, 25, 17, 2))
                .activateStatus(ActivateStatus.ACTIVATED)
                .participants(new Participants(List.of(new Participant(1, 1, ParticipantRole.ROOM_MANAGER, LocalDateTime.of(2023, 2, 25, 17,2)))))
                .maxParticipants(10)
                .build();

        StudyRoom mockStudyRoom2 = StudyRoom.builder()
                .roomId(2)
                .roomName("My Test Room2")
                .roomCreateDateTime(LocalDateTime.of(2023, 2, 25, 19, 3))
                .activateStatus(ActivateStatus.ACTIVATED)
                .participants(new Participants(List.of(new Participant(2, 2, ParticipantRole.ROOM_MANAGER, LocalDateTime.of(2023, 2, 25, 17,2)))))
                .maxParticipants(15)
                .build();

        List<StudyRoom> studyRooms = List.of(mockStudyRoom1, mockStudyRoom2);

        given(studyRoomService.getEnterAvailableStudyRooms())
                .willReturn(studyRooms);

        //when
        ResultActions resultActions =
                mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/rooms/enter-available"));

        //then
        MvcResult mvcResult = resultActions
                .andExpect(status().isOk())
                .andReturn();

        verify(studyRoomService, Mockito.times(1)).getEnterAvailableStudyRooms();

        //System.out.println("mvc result :" + mvcResult.getResponse().getContentAsString());
    }

    @Test
    @DisplayName("GET /api/v1/rooms/info api 메서드 호출 확인, http 응답 확인, 서비스 레이어의 비즈니스 로직 호출 확인 테스트")
    void getRoomInfoTest() throws Exception {
        //given
        int roomId = 1;

        StudyRoom mockStudyRoom = StudyRoom.builder()
                .roomId(1)
                .roomName("My Test Room")
                .roomCreateDateTime(LocalDateTime.of(2023, 2, 25, 17, 2))
                .activateStatus(ActivateStatus.ACTIVATED)
                .participants(new Participants(List.of(new Participant(1, 1, ParticipantRole.ROOM_MANAGER, LocalDateTime.of(2023, 2, 25, 17,2)))))
                .maxParticipants(10)
                .build();

        given(studyRoomService.getRoomInformation(roomId))
                .willReturn(mockStudyRoom);

        //when, then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/rooms/info")
                    .contentType(MediaType.APPLICATION_JSON)
                    .param("roomId", String.valueOf(roomId)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.roomId").value(mockStudyRoom.getRoomId()))
                .andExpect(jsonPath("$.roomName").value(mockStudyRoom.getRoomName()))
                .andExpect(jsonPath("$.maxParticipants").value(mockStudyRoom.getMaxParticipants()))
                .andExpect(jsonPath("$.currentParticipantsCount").value(mockStudyRoom.getCurrentParticipantsCount()))
                .andExpect(jsonPath("$.roomManagerSequenceId").value(mockStudyRoom.getRoomManager().getMemberSequenceId()))
                .andDo(print());

        verify(studyRoomService, Mockito.times(1)).getRoomInformation(roomId);
    }

    @Test
    @DisplayName("GET /api/v1/rooms/info 주어진 매개 변수 형식이 잘못된 경우 결과로 BAD REQUEST, InvalidFormatException 반환")
    void getRoomInfoMethodInvalidParameterFormatTest() throws Exception {
        //given
        String invalidFormatRoomId = "cannotConvertToIntValue";

        //when, then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/rooms/info")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("roomId", invalidFormatRoomId))
                .andExpect(status().isBadRequest())
                .andExpect(result ->
                        result.getResolvedException().getClass().isAssignableFrom(InvalidFormatException.class));

    }
}