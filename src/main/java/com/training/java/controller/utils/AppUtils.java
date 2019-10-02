package com.training.java.controller.utils;

import com.training.java.model.entity.UserAccount;
import javax.servlet.http.HttpSession;

public class AppUtils {

    private  static  String uriToRedirect;

    public static void storeLoginedUser(HttpSession session, UserAccount loginedUser) {
        session.setAttribute("loginedUser", loginedUser);
    }

    public static UserAccount getLoginedUser(HttpSession session) {
        return (UserAccount) session.getAttribute("loginedUser");
    }

    public static void storeRedirectAfterLoginUrl(String requestUri) {
        uriToRedirect=requestUri;
    }

    public static String getRedirectAfterLoginUrl() {
        return uriToRedirect;

    }

}