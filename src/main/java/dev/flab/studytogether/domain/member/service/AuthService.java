package dev.flab.studytogether.domain.member.service;

import javax.servlet.http.HttpSession;

public class AuthService {

    public static final String ROOM_ADMIN_ROLE = "ROOM_ADMIN";

    private HttpSession httpSession;

    public AuthService(HttpSession httpSession) {
        this.httpSession = httpSession;
    }

    /*
    * Room Admin 권한 부여
    * */
    public void grantRoomAdminRole() {
        this.httpSession.setAttribute(ROOM_ADMIN_ROLE, true);
    }

    /*
     * Room Admin 권한 회수
     * */
    public void revokeRoomAdminRole() {
        this.httpSession.removeAttribute(ROOM_ADMIN_ROLE);
    }

    public boolean isRoomAdmin() {
        Object roomAdminAttribute = this.httpSession.getAttribute(ROOM_ADMIN_ROLE);
        return roomAdminAttribute != null && (Boolean) roomAdminAttribute;
    }

}
