package dev.flab.studytogether.domain.room.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import dev.flab.studytogether.domain.room.dto.ParticipantResponse;
import dev.flab.studytogether.domain.room.dto.StudyRoomMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.lang.reflect.Type;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql({"classpath:schema.sql", "classpath:test-data.sql"})
class StompStudyRoomControllerTest {
    @LocalServerPort
    private int port;
    private String url;
    private StompSession stompSession;
    private ObjectMapper objectMapper;
    private static String END_POINT_PREFIX = "/stomp-room";

    @BeforeEach
    void setup() throws ExecutionException, InterruptedException, TimeoutException {
        this.url = "ws://localhost:" + port + END_POINT_PREFIX;

        //WebSocket Client Setup
        WebSocketStompClient stompClient =
                new WebSocketStompClient(new SockJsClient(createTransportClient()));

        MappingJackson2MessageConverter messageConverter = new MappingJackson2MessageConverter();
        this.objectMapper = messageConverter.getObjectMapper();
        this.objectMapper.registerModule(new ParameterNamesModule());
        stompClient.setMessageConverter(messageConverter);

        //WebSocket Connection
        this.stompSession =
                stompClient.connect(url, new StompSessionHandlerAdapter() {}).get(1, TimeUnit.SECONDS);

    }

    @Test
    @DisplayName("스터디 룸 입장 메시지를 보내면, 해당 방의 구독자들에게 greeting 메시지가 전송된다.")
    void whenSendingMessageForEnteringRoom_thenGreetingMessageSentToSubscriber() throws ExecutionException, InterruptedException, TimeoutException {
        //given
        String roomId = "1";
        String memberId = "user2";
        int memberSequenceId = 2;
        String greetingMessageFormat = "님이 입장하셨습니다.";

        CompletableFuture<StudyRoomMessage> completableFuture = new CompletableFuture<>();

        //when
        stompSession.subscribe("/subscribe/message/room/" + roomId, new StompFrameHandler() {
            @Override
            public Type getPayloadType(StompHeaders headers) {
                return StudyRoomMessage.class;
            }

            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                completableFuture.complete((StudyRoomMessage) payload);
            }
        });
        stompSession.send("/publish/room/enter",
                new StudyRoomMessage(roomId, memberId, memberSequenceId));


        StudyRoomMessage receivedMessage = completableFuture.get(10, TimeUnit.SECONDS);

        //then
        assertEquals(receivedMessage.getStudyRoomID(), roomId);
        assertEquals(receivedMessage.getMemberID(), memberId);
        assertEquals(receivedMessage.getMessage(), memberId + greetingMessageFormat);
    }
    
    @Test
    @DisplayName("스터디룸 참여자 목록 업데이트 시 클라이언트에게 전송한다.")
    void whenParticipantsListUpdatedThenSendToClients() throws ExecutionException, InterruptedException, TimeoutException {
        //given
        String roomId = "1";
        String memberId = "user2";
        int memberSequenceId = 2;

        CompletableFuture<List<ParticipantResponse>> completableFuture = new CompletableFuture<>();

        //when
        stompSession.subscribe("/subscribe/participants/room/" + roomId, new StompFrameHandler() {
            @Override
            public Type getPayloadType(StompHeaders headers) {
                return new ParameterizedTypeReference<List<ParticipantResponse>>() {}.getType();
            }

            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                List<ParticipantResponse> receivedParticipants = ((List<Map<String, Object>>) payload).stream()
                        .map(participant -> objectMapper.convertValue(participant, ParticipantResponse.class))
                        .collect(Collectors.toList());

                completableFuture.complete(receivedParticipants);
            }
        });

        stompSession.send("/publish/room/enter",
                new StudyRoomMessage(roomId, memberId, memberSequenceId));

        List<ParticipantResponse> participants = completableFuture.get(10, TimeUnit.SECONDS);

        Optional<ParticipantResponse> participant= participants.stream()
                        .filter(p -> p.getMemberSequenceID() == memberSequenceId && p.getMemberID().equals(memberId))
                        .findFirst();

        assertTrue(participant.isPresent());
    }

    @Test
    @DisplayName("다른 방에 입장하는 경우 메시지를 받지 않는다.")
    void whenEnteringDifferentRoomThenNoMessageReceived() throws ExecutionException, InterruptedException, TimeoutException {
        //given
        String broadcastRoomId = "1";
        String enterRoomId = "2";
        String memberId = "memberId";
        int memberSequenceId = 1;

        CompletableFuture<StudyRoomMessage> completableFuture = new CompletableFuture<>();

        //when
        stompSession.subscribe("/subscribe/message/room/" + enterRoomId, new StompFrameHandler() {
            @Override
            public Type getPayloadType(StompHeaders headers) {
                return StudyRoomMessage.class;
            }

            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                completableFuture.complete((StudyRoomMessage) payload);
            }
        });
        stompSession.send("/publish/room/enter",
                new StudyRoomMessage(broadcastRoomId, memberId, memberSequenceId));

        Optional<StudyRoomMessage> receivedMessage;
        try {
            receivedMessage = Optional.of(completableFuture.get(10, TimeUnit.SECONDS));
        } catch (TimeoutException e) {
            receivedMessage = null;
        }

        //then
        assertNull(receivedMessage);
    }
    
    @Test
    @DisplayName("스터디 룸 퇴장 메시지를 보내면, 해당 방의 구독자들에게 퇴장 메시지가 전송된다.")
    void exitMethodTest() throws ExecutionException, InterruptedException, TimeoutException {
        String roomId = "1";
        String memberId = "memberId";
        int memberSequenceId = 1;
        String greetingMessageFormat = "님이 퇴장하셨습니다.";

        CompletableFuture<StudyRoomMessage> completableFuture = new CompletableFuture<>();

        //when
        stompSession.subscribe("/subscribe/message/room/" + roomId, new StompFrameHandler() {
            @Override
            public Type getPayloadType(StompHeaders headers) {
                return StudyRoomMessage.class;
            }

            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                completableFuture.complete((StudyRoomMessage) payload);
            }
        });
        stompSession.send("/publish/room/exit",
                new StudyRoomMessage(roomId, memberId, memberSequenceId));

        StudyRoomMessage receivedMessage = completableFuture.get(10, TimeUnit.SECONDS);

        //then
        assertEquals(receivedMessage.getStudyRoomID(), roomId);
        assertEquals(receivedMessage.getMemberID(), memberId);
        assertEquals(receivedMessage.getMessage(), memberId + greetingMessageFormat);

    }
    
    @Test
    @DisplayName("다른 방에서 퇴장하는 경우에는 메시지를 받지 않는다.")
    void whenExitingDifferentRoomThenNoMessageReceived() throws ExecutionException, InterruptedException, TimeoutException {
        //given
        String broadcastRoomId = "1";
        String exitRoomId = "2";
        String memberId = "memberId";
        int memberSequenceId = 1;

        CompletableFuture<StudyRoomMessage> completableFuture = new CompletableFuture<>();

        //when
        stompSession.subscribe("/subscribe/message/room/" + exitRoomId, new StompFrameHandler() {
            @Override
            public Type getPayloadType(StompHeaders headers) {
                return StudyRoomMessage.class;
            }

            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                completableFuture.complete((StudyRoomMessage) payload);
            }
        });
        stompSession.send("/publish/room/exit",
                new StudyRoomMessage(broadcastRoomId, memberId, memberSequenceId));

        Optional<StudyRoomMessage> receivedMessage;
        try {
            receivedMessage = Optional.of(completableFuture.get(10, TimeUnit.SECONDS));
        } catch (TimeoutException e) {
            receivedMessage = null;
        }

        //then
        assertNull(receivedMessage);
    }

    private List<Transport> createTransportClient() {
        List<Transport> transports = new ArrayList<>();
        transports.add(new WebSocketTransport(new StandardWebSocketClient()));
        return transports;
    }
}