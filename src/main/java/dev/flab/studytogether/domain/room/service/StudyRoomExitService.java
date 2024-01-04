//package dev.flab.studytogether.domain.room.service;
//
//import dev.flab.studytogether.domain.room.entity.StudyRoom;
//import dev.flab.studytogether.domain.room.exception.RoomNotFoundException;
//import dev.flab.studytogether.domain.room.repository.ParticipantRepository;
//import dev.flab.studytogether.domain.room.repository.StudyRoomRepository;
//import org.springframework.stereotype.Service;
//
//@Service
//public class StudyRoomExitService {
//
//    private final AuthService authService;
//    private final StudyRoomRepository studyRoomRepository;
//    private final ParticipantRepository participantRepository;
//
//    public StudyRoomExitService(AuthService authService, StudyRoomRepository studyRoomRepository, ParticipantRepository participantRepository) {
//        this.authService = authService;
//        this.studyRoomRepository = studyRoomRepository;
//        this.participantRepository = participantRepository;
//    }
//
//    public StudyRoom exitRoom(int roomId, int memberSequenceId) {
//        StudyRoom studyRoom = studyRoomRepository.findByRoomId(roomId).orElseThrow(() ->
//                new RoomNotFoundException("방이 존재하지 않습니다."));
//
//        if (authService.isRoomAdmin()) {
//            transferRoomAdminRole(memberSequenceId, roomId);
//        }
//
//        studyRoom.exitRoom();
//
//        participantRepository.delete(studyRoom.getRoomId(), memberSequenceId);
//        studyRoomRepository.update(studyRoom.getRoomId(),
//                studyRoom.getRoomName(),
//                studyRoom.getMaxParticipants(),
//                studyRoom.getCurrentParticipants(),
//                studyRoom.getManagerSequenceId());
//
//        return studyRoom;
//    }
//
//    /*
//    room admin 권한을 가졌을 시 권한은 다른 사용자에게 위임 하는 메서드(임시)
//    * */
//    private void transferRoomAdminRole(int currentAdminSequenceID, int roomId) {
////        Optional<Participant> nextAdmin = findNextAdmin(currentAdminSequenceID, roomId);
////
////        if (nextAdmin.isPresent()) {
////
////        }
//
//    }
//
////    private Optional<Participant> findNextAdmin(int currentAdminSequenceID, int roomId) {
////        List<Participant> participants = participantRepository.findByRoomId(roomId);
////
////        return participants.stream()
////                .filter(participant -> participant.getSequenceId() != currentAdminSequenceID)
////                .min(Comparator.comparing(Participant::getEntryTime));
////    }
//}
