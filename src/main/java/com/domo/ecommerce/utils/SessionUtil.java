package com.domo.ecommerce.utils;

import javax.servlet.http.HttpSession;

public class SessionUtil {

    private static final String LOGIN_MEMBER_ID = "LOGIN_MEMBER_ID";
    private static final String LOGIN_ADMIN_ID = "LOGIN_ADMIN_ID";

    /* 인스턴스화 방지 */
    private SessionUtil() {
    }

    /**
     * 로그인 한 회원의 id를 세션에 저장한다.
     * @param session 사용자 세션
     * @param id 로그인한 회원의 id
     */
    public static void setLoginMemberId(HttpSession session, String id) {
        session.setAttribute(LOGIN_MEMBER_ID, id);
    }

    /**
     * 로그인 한 회원의 id를 세션에서 가져온다.
     * @param session 사용자 세션
     * @return 로그인한 회원의 id or null
     */
    public static String getLoginMemberId(HttpSession session) {
        return (String) session.getAttribute(LOGIN_MEMBER_ID);
    }

    /**
     * 해당 세션의 정보를 모두 삭제한다.
     *
     * @param session 사용자 세션
     */
    public static void clear(HttpSession session) {
        session.invalidate();
    }

    /**
     * 로그인 한 관리자의 id를 세션에 저장한다.
     * @param session 사용자 세션
     * @param id 로그인한 관리자의 id
     */
    public static void setLoginAdminId(HttpSession session, String id) {
        session.setAttribute(LOGIN_ADMIN_ID, id);
    }

    /**
     * 로그인 한 관리자의 id를 세션에서 가져온다.
     * @param session 사용자 세션
     * @return 로그인한 관리자의 id or null
     */
    public static String getLoginAdminId(HttpSession session) {
        return (String) session.getAttribute(LOGIN_ADMIN_ID);
    }

    /**
     * 로그인 한 회원 정보를 삭제한다.
     *
     * @param session 사용자 세션
     */
    public static void logoutMember(HttpSession session) {
        session.removeAttribute(LOGIN_MEMBER_ID);
    }

    /**
     * 로그인 한 관리자의 정보를 삭제한다.
     *
     * @param session
     */
    public static void logoutAdmin(HttpSession session) {
        session.removeAttribute(LOGIN_ADMIN_ID);
    }
}
