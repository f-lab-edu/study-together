package dev.flab.simpleweather.utils;

import javax.servlet.http.HttpSession;

public class SessionUtil {

    //인스턴스화 방지
    private SessionUtil(){}

    public static int getLoginMemebrSeqId(HttpSession httpSession){
        return (int) httpSession.getAttribute("seq_id");
    }

    public static void logoutMemebr(HttpSession httpSession){
        httpSession.removeAttribute("seq_id");
        httpSession.removeAttribute("id");
    }

    public static void setloginMemberSession(HttpSession httpSession, String id, int seqId){
        httpSession.setAttribute("id", id);
        httpSession.setAttribute("seq_id", seqId);
    }
}
